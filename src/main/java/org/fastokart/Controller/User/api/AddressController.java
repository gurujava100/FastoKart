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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-account/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("/default")
    public AddressResponseDTO getDefaultAddress(HttpSession session){

        Long userId = (Long) session.getAttribute("USER_ID");

        if(userId == null){
            throw new RuntimeException("User not logged in");
        }

        return addressService.getDefaultAddress(userId);
    }
    @GetMapping
    public List<AddressResponseDTO> getAllAddresses(HttpSession session){

        Long userId = (Long) session.getAttribute("USER_ID");

        return addressService.getAllAddresses(userId);
    }
    @PostMapping("add")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequestDTO dto, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login required");
        }

        AddressResponseDTO saved = addressService.addAddress(dto, userId);

        return ResponseEntity.ok(saved);
    }
}
