package com.springDevelopers.Backend.Services;

import com.springDevelopers.Backend.DTO.AccessKeyDTO;
import com.springDevelopers.Backend.Entities.AccessKey;
import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Repositories.AccessKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessKeyService {
    @Autowired
    private final AccessKeyRepository accessKeyRepository;

    public AccessKeyService(AccessKeyRepository accessKeyRepository) {
        this.accessKeyRepository = accessKeyRepository;
    }
    public void addAccessKey(AccessKey accessKey){
        accessKeyRepository.save(accessKey);
    }
    public List<AccessKey> getAllAccessKeyByUser(User user){
        return this.accessKeyRepository.findAccessKeyByUser(user);
    }

    public AccessKey findAccessKeyById(Integer Id){
        return this.accessKeyRepository.findAccessKeyById(Id);
    }
    public List<AccessKeyDTO> getAllAccessKeys() {
        List<AccessKey> accessKeys = accessKeyRepository.findAll();
        return accessKeys.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AccessKeyDTO convertToDTO(AccessKey accessKey) {
        AccessKeyDTO dto = new AccessKeyDTO();
        dto.setId(accessKey.getId());
        dto.setKey(accessKey.getKeyName());
        dto.setStatus(accessKey.getStatus());
        dto.setDateOfProcurement(accessKey.getDateOfProcurement());
        dto.setExpiryDate(accessKey.getExpiryDate());
        dto.setSchoolEmail(accessKey.getUser().getSchoolEmail());
        return dto;
    }
}
