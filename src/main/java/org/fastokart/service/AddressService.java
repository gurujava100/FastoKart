package org.fastokart.service;

import org.fastokart.dto.AddressRequestDTO;
import org.fastokart.dto.AddressResponseDTO;
import org.fastokart.model.UserModel;

import java.util.List;

public interface AddressService {
    AddressResponseDTO addAddress(AddressRequestDTO dto, UserModel user);

     List<AddressResponseDTO> getAllAddresses(Long userId);

   // AddressResponseDTO updateAddress(Long id, AddressRequestDTO dto, User user);

  //  void deleteAddress(Long id, User user);

  //  AddressResponseDTO setDefaultAddress(Long id, User user);
}
