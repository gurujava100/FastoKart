package org.fastokart.mapper;

import org.fastokart.dto.AddressRequestDTO;
import org.fastokart.dto.AddressResponseDTO;
import org.fastokart.model.AddressModel;
import org.fastokart.model.UserModel;

public class AddressMapper {
    // Request DTO → Entity
    public static AddressModel toEntity(AddressRequestDTO dto, UserModel user) {
        AddressModel address = new AddressModel();
        address.setName(dto.getName());
        address.setPhone(dto.getPhone());
        address.setPincode(dto.getPincode());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setFullAddress(dto.getFullAddress());
        address.setLandmark(dto.getLandmark());
        address.setUser(user);
        return address;
    }
    // Entity → Response DTO
    public static AddressResponseDTO toDTO(AddressModel address) {

        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setName(address.getName());
        dto.setPhone(address.getPhone());
        dto.setPincode(address.getPincode());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setFullAddress(address.getFullAddress());
        dto.setLandmark(address.getLandmark());
        dto.setDefault(address.isDefault());

        return dto;
    }
    // UPDATE (🔥 IMPORTANT)
    public static void updateEntity(AddressModel address, AddressRequestDTO dto) {
        address.setName(dto.getName());
        address.setPhone(dto.getPhone());
        address.setPincode(dto.getPincode());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setFullAddress(dto.getFullAddress());
        address.setLandmark(dto.getLandmark());
        address.setDefault(dto.isDefault());
    }

}
