package com.springDevelopers.Backend.Controllers;

import com.springDevelopers.Backend.DTO.AccessKeyDTO;
import com.springDevelopers.Backend.DTO.AccessKeysDTO;
import com.springDevelopers.Backend.DTO.Counter;
import com.springDevelopers.Backend.Entities.AccessKey;
import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Enums.Status;
import com.springDevelopers.Backend.Repositories.UserRepository;
import com.springDevelopers.Backend.Services.AccessKeyService;
import com.springDevelopers.Backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final AccessKeyService accessKeyService;
    private final UserService userService;
    private final UserRepository userRepository;



    @PostMapping("/generateKey/{Id}")
    public ResponseEntity<Boolean> addAccessKeyByUser(@PathVariable Integer Id){
        User user = this.userService.findByUserId(Id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        boolean hasActiveKey = accessKeyService.getAllAccessKeyByUser(user).stream()
                .anyMatch(accessKey -> accessKey.getStatus() == Status.ACTIVE);
        if (hasActiveKey) {
            return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
        }
        Counter counter = new Counter();
        String userEmail = user.getEmail();
        String keyName = userEmail.substring(0, userEmail.indexOf('@') - 1) + counter.getCountNumber(user.getId());
        AccessKey accessKey = new AccessKey();
        accessKey.setKeyName(keyName);
        accessKey.setStatus(Status.ACTIVE);
        accessKey.setDateOfProcurement(LocalDate.now());
        accessKey.setExpiryDate(LocalDate.now().plusDays(1));
        accessKey.setUser(user);
        this.accessKeyService.addAccessKey(accessKey);

        return new ResponseEntity<>(false, HttpStatus.CREATED);

    }
    @GetMapping("/accessKeys/{Id}")
    public ResponseEntity<List<AccessKeysDTO>> listOfAccessKeysByUser(@PathVariable Integer Id){
        User user = this.userService.findByUserId(Id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        List<AccessKey> accessKeysByUser = this.accessKeyService.getAllAccessKeyByUser(user);
        List<AccessKeysDTO> accessKeysDTOList = accessKeysByUser.stream()
                .map(accessKey -> new AccessKeysDTO(
                        accessKey.getId(),
                        accessKey.getKeyName(),
                        accessKey.getStatus().toString(),
                        accessKey.getDateOfProcurement(),
                        accessKey.getExpiryDate()
                )).collect(Collectors.toList());
        return new ResponseEntity<>(accessKeysDTOList, HttpStatus.OK);

    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<?>  validateExpiryDate(@PathVariable Integer userId){
        try{
            User user = this.userService.findByUserId(userId);
            if(user == null){
                return ResponseEntity.notFound().build();
            }
            List<AccessKey> listOfAccessKeyByUser = this.accessKeyService.getAllAccessKeyByUser(user);
            for(AccessKey accessKey: listOfAccessKeyByUser){
                if(accessKey.getExpiryDate().isBefore(LocalDate.now()) && accessKey.getStatus() == Status.ACTIVE){
                    accessKey.setStatus(Status.EXPIRED);
                    this.accessKeyService.addAccessKey(accessKey);

                }
            }
            return ResponseEntity.ok().build();

        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry an error occurred");

        }

    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getUser(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }


}
