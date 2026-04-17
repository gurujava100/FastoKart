package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.AddressRequestDTO;
import org.fastokart.dto.AddressResponseDTO;
import org.fastokart.model.AddressModel;
import org.fastokart.model.UserModel;
import org.fastokart.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-account/address/")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("add")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequestDTO dto, HttpSession session) {

        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login required");
        }


        AddressResponseDTO saved = addressService. addAddress(dto,user);
        return ResponseEntity.ok(saved);
    }
}
