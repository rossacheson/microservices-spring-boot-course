package com.rossacheson.cards.controller;

import com.rossacheson.cards.constants.CardsConstants;
import com.rossacheson.cards.dto.ResponseDto;
import com.rossacheson.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CardsController {
    private ICardsService iCardsService;

    @Operation(
            summary = "Create card REST API",
            description = "REST API to create a card based on a mobile number"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }
}
