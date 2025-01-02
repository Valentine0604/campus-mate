//package org.pollub.campusmate.utilities.security.auth;
//
//import com.google.zxing.WriterException;
//import jakarta.servlet.ServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.pollub.campusmate.user.entity.User;
//import org.pollub.campusmate.user.exception.UserNotFound;
//import org.pollub.campusmate.user.repository.UserRepository;
//import org.pollub.campusmate.utilities.service.BackupCodeService;
//import org.pollub.campusmate.utilities.service.TwoFactorAuthService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.imageio.ImageIO;
//import java.io.ByteArrayOutputStream;
//import java.util.Base64;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/2fa")
//@RequiredArgsConstructor
//public class TwoFactorAuthController {
//
//    private final UserRepository userRepository;
//    private final TwoFactorAuthService twoFactorAuthenticationService;
//    private final BackupCodeService backupCodeService;
//
//    @PostMapping("/enable")
//    public ResponseEntity<?> enableTwoFactorAuthentication(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
//
//        String username = userDetails.getUsername();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UserNotFound("User with email " + username + " not found"));
//
//        String secretKey = twoFactorAuthenticationService.generateSecretKey();
//        user.setTwoFactorEnabled(true);
//        user.setTwoFactorSecret(secretKey);
//
//        List<String> rawBackupCodes = backupCodeService.generateBackupCodes();
//        List<String> hashedCodes = backupCodeService.hashBackupCodes(rawBackupCodes);
//        user.setBackupCodes(hashedCodes);
//
//        userRepository.save(user);
//
//        String totpUrl = twoFactorAuthenticationService.getTOTPUrl(username, secretKey);
//
//        try{
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            ImageIO.write(twoFactorAuthenticationService.generateQRCodeImage(totpUrl), "png", stream);
//            String qrCode = Base64.getEncoder().encodeToString(stream.toByteArray());
//
//            return ResponseEntity.ok(Map.of(
//                    "secretKey", secretKey,
//                    "qrCode", qrCode,
//                    "backupCodes", rawBackupCodes
//            ));
//
//        } catch(Exception e){
//            return ResponseEntity.status(500).body("Error generating QR code");
//        }
//    }
//
//    @PostMapping("/disable")
//    public ResponseEntity<String> disableTwoFactorAuthentication(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
//        String username = userDetails.getUsername();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UserNotFound("User with email " + username + " not found"));
//
//        user.setTwoFactorEnabled(false);
//        user.setTwoFactorSecret(null);
//        userRepository.save(user);
//
//        return ResponseEntity.ok("Two-factor authentication disabled");
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyTwoFactorAuthentication(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String otpOrBackupCode) throws Exception {
//        String username = userDetails.getUsername();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UserNotFound("User with email " + username + " not found"));
//
//        if(!user.isTwoFactorEnabled()){
//            return ResponseEntity.badRequest().body("Two-factor authentication is not enabled for this user");
//        }
//
//        if(twoFactorAuthenticationService.isOTP(otpOrBackupCode)){
//            boolean isValid = twoFactorAuthenticationService.verifyTOTP(user.getTwoFactorSecret(), otpOrBackupCode);
//
//            if(!isValid){
//                return ResponseEntity.status(403).body("Invalid TOTP code");
//            }
//        }
//        else{
//            boolean isBackupCodeValid = backupCodeService.verifyBackupCode(otpOrBackupCode, user.getBackupCodes());
//
//            if(!isBackupCodeValid){
//                return ResponseEntity.status(400).body("Invalid backup code");
//            }
//
//            List<String> remainingCodes = user.getBackupCodes().stream()
//                    .filter(code -> !backupCodeService.verifyBackupCode(otpOrBackupCode, List.of(code)))
//                    .toList();
//
//            user.setBackupCodes(remainingCodes);
//            userRepository.save(user);
//        }
//
//        return ResponseEntity.ok("Two-factor authentication verified");
//    }
//
//    @PostMapping("/regenerate-backup-codes")
//    public ResponseEntity<List<String>> regenerateBackupCodes(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
//        String username = userDetails.getUsername();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UserNotFound("User with email " + username + " not found"));
//
//        if(!user.isTwoFactorEnabled()){
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        List<String> rawBackupCodes = backupCodeService.generateAndSaveBackupCodes(user);
//
//        userRepository.save(user);
//
//        return ResponseEntity.ok(rawBackupCodes);
//    }
//
//
//}
