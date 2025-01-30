package fr.perpetualmotion.airbnbback.listing.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.perpetualmotion.airbnbback.listing.application.dto.CreatedListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.SaveListingDTO;
import fr.perpetualmotion.airbnbback.listing.application.dto.sub.PictureDTO;
import fr.perpetualmotion.airbnbback.listing.application.service.LandLordService;
import fr.perpetualmotion.airbnbback.user.application.service.UserException;
import fr.perpetualmotion.airbnbback.user.application.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/landlord-listing")
public class LandlordResource {
    private final LandLordService landLordService;

    private final Validator validator;

    private final UserService  userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public LandlordResource(LandLordService landLordService, Validator validator, UserService userService) {
        this.landLordService = landLordService;
        this.validator = validator;
        this.userService = userService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatedListingDTO> create(MultipartHttpServletRequest request,
                                                    @RequestPart(name="dto")String saveListingDTOString) throws IOException {
        List<PictureDTO> pictures = request.getFileMap()
                .values()
                .stream()
                .map(mapMultiPartFileToPictureDTO())
                .toList();

        SaveListingDTO saveListingDTO = objectMapper.readValue(saveListingDTOString, SaveListingDTO.class);
        saveListingDTO.setPictures(pictures);

        Set<ConstraintViolation<SaveListingDTO>> violations = validator.validate(saveListingDTO);
        if(!violations.isEmpty()){
            String violationJoined = violations.stream()
                    .map(violation -> violation.getPropertyPath()+ " "+ violation.getMessage())
                    .collect(Collectors.joining());
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, violationJoined);
            return ResponseEntity.of(problemDetail).build();
        }else {
            return ResponseEntity.ok(landLordService.create(saveListingDTO));
        }
    }

    private static Function<MultipartFile, PictureDTO> mapMultiPartFileToPictureDTO(){
        return multipartFile -> {
            try{
                return new PictureDTO(multipartFile.getBytes(), multipartFile.getContentType(), false);
            }catch (IOException ioe){
                throw new UserException(String.format("Cannot parse multipart file :%s", multipartFile.getOriginalFilename()));
            }
        };
    }
}
