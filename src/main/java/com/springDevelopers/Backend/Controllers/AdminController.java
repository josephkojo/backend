package com.springDevelopers.Backend.Controllers;

import com.springDevelopers.Backend.DTO.AccessKeyDTO;
import com.springDevelopers.Backend.Entities.AccessKey;
import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Enums.Status;
import com.springDevelopers.Backend.Repositories.AccessKeyRepository;
import com.springDevelopers.Backend.Services.AccessKeyService;
import com.springDevelopers.Backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AccessKeyService accessKeyService;
    private final UserService userService;
    private  final AccessKeyRepository accessKeyRepository;
    @GetMapping("/getAllKeys")
    public ResponseEntity<List<AccessKeyDTO>> getAllKeysGenerated(){
        List<AccessKeyDTO> accessKeyDTOList = this.accessKeyService.getAllAccessKeys();
        return new ResponseEntity<>(accessKeyDTOList, HttpStatus.OK);
    }

    @GetMapping("/getActiveKey/{email}")
    public ResponseEntity<AccessKey> getActiveKey(@PathVariable String email){
        User user = this.userService.findUserBySchoolEmail(email);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        List<AccessKey> accessKeyList = this.accessKeyService.getAllAccessKeyByUser(user);
        AccessKey activeAccessKey = null;
        for(AccessKey accessKey: accessKeyList){
            if(accessKey.getStatus() == Status.ACTIVE){
                activeAccessKey = accessKey;
            }
        }
        return activeAccessKey == null ? ResponseEntity.status(404).build() : new ResponseEntity<>(activeAccessKey, HttpStatusCode.valueOf(200));

    }
    @PutMapping("/revokeKey/{Id}")
    public ResponseEntity<AccessKeyDTO> revokeKey(@PathVariable Integer Id){
        AccessKey accessKey = accessKeyService.findAccessKeyById(Id);
        if(accessKey == null){
            return ResponseEntity.noContent().build();
        }
        accessKey.setStatus(Status.REVOKED);
        this.accessKeyService.addAccessKey(accessKey);
        AccessKeyDTO accessKeyDTO = convertToDTO(accessKey);
        return new ResponseEntity<>(accessKeyDTO, HttpStatus.OK);
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
    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateKeyStatus(){
        List<AccessKey> AllKeyGenerated = this.accessKeyRepository.findAll();
        for(AccessKey accessKey:AllKeyGenerated ){
            if(accessKey.getStatus() == Status.ACTIVE && accessKey.getExpiryDate().equals(LocalDate.now())){
                accessKey.setStatus(Status.EXPIRED);
                this.accessKeyRepository.save(accessKey);
            }
        }
        return new ResponseEntity<>("Update method was successful", HttpStatus.OK);

    }
}
