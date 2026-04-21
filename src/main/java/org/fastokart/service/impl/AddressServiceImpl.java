package org.fastokart.service.impl;

import org.fastokart.dto.AddressRequestDTO;
import org.fastokart.dto.AddressResponseDTO;
import org.fastokart.mapper.AddressMapper;
import org.fastokart.model.AddressModel;
import org.fastokart.model.UserModel;
import org.fastokart.repository.AddressRepository;
import org.fastokart.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    // ✅ Add Address
    @Override
    public AddressResponseDTO addAddress(AddressRequestDTO dto, Long userId) {

        // 🔹 Create lightweight user object
        UserModel user = new UserModel();
        user.setId(userId);

        AddressModel address = AddressMapper.toEntity(dto, user);

        // 🔥 RULE 1: First address → default
        if (addressRepository.countByUser(user) == 0) {
            address.setDefault(true);
        }

        // 🔥 RULE 2: If user selects default → reset old default
        if (dto.isDefault()) {
            addressRepository.resetDefaultAddresses(userId);
            address.setDefault(true);
        }

        AddressModel saved = addressRepository.save(address);

        return AddressMapper.toDTO(saved);
    }
    public List<AddressResponseDTO> getAllAddresses(Long userId) {

        List<AddressModel> addresses = addressRepository.findByUserId(userId);

        return addresses.stream()
                .map(AddressMapper::toDTO)
                .toList();
    }
    @Override
    public AddressResponseDTO getDefaultAddress(Long userId) {

        AddressModel address = addressRepository
                .findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new RuntimeException("Default address not found"));

        return AddressMapper.toDTO(address);
    }

    @Override
    public AddressResponseDTO getAddressById(Long addressId) {

        AddressModel address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return AddressMapper.toDTO(address);
    }

}
