package org.pollub.campusmate.utilities.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.pollub.campusmate.utilities.constants.BackupCode.BACKUP_CODE_COUNT;
import static org.pollub.campusmate.utilities.constants.BackupCode.BACKUP_CODE_LENGTH;

@Service
@RequiredArgsConstructor
public class BackupCodeService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<String> generateBackupCodes(){
        SecureRandom secureRandom = new SecureRandom();
        List<String> backupCodes = new ArrayList<>();

        for(int i = 0; i < BACKUP_CODE_COUNT; i++){
            StringBuilder code = new StringBuilder();

            for(int j = 0; j < BACKUP_CODE_LENGTH; j++){
                code.append(secureRandom.nextInt(10));
            }

            backupCodes.add(code.toString());
        }

        return backupCodes;
    }

    public List<String> hashBackupCodes(List<String> backupCodes){
        List<String> hashedBackupCodes = new ArrayList<>();

        for(String code : backupCodes){
            hashedBackupCodes.add(passwordEncoder.encode(code));
        }

        return hashedBackupCodes;
    }

    public boolean verifyBackupCode(String rawCode, List<String> hashedBackupCodes){
        for(String hashedCode : hashedBackupCodes){
            if(passwordEncoder.matches(rawCode, hashedCode)){
                return true;
            }
        }

        return false;
    }
}
