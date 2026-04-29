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
        System.out.println("👉 ADDRESS API CALLED");
        System.out.println("Session ID (address): " + session.getId());
        System.out.println("USER_ID from session: " + session.getAttribute("USER_ID"));
        Long userId = (Long) session.getAttribute("USER_ID");

        return addressService.getAllAddresses(userId);
    }
    @PostMapping("add")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequestDTO dto, HttpSession session) {
        System.out.println("👉 ADD ADDRESS API CALLED");
        System.out.println("Session ID: " + session.getId());
        System.out.println("USER_ID: " + session.getAttribute("USER_ID"));
        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login required");
        }

        AddressResponseDTO saved = addressService.addAddress(dto, userId);

        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddressApi(@PathVariable Long id,
                                               @RequestBody AddressRequestDTO dto,
                                               HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        AddressResponseDTO response = addressService.updateAddress(id, dto, userId);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id, HttpSession session) {

        // 1. Check login
        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login first");
        }

        try {
            // 2. Call service
            addressService.deleteAddress(id, userId);

            // 3. Success response
            return ResponseEntity.ok("Address deleted successfully");

        } catch (RuntimeException ex) {
            // 4. Handle errors cleanly
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }
}
