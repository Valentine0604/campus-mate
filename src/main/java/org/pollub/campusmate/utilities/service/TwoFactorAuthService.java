package org.pollub.campusmate.utilities.service;

import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.Totp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

import static java.lang.String.format;

@Service
public class TwoFactorAuthService {

    public String generateSecretKey(){
        return Base32.random();
    }

    public String getTOTPUrl(String username, String secretKey){
        return String.format("otpauth://totp/%s?secret=%s&issuer=CampusMate", username, secretKey);
    }

    public BufferedImage generateQRCodeImage(String totpUrl) throws WriterException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(totpUrl,
                BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public boolean verifyTOTP(String secretKey, String code){
        Totp totp = new Totp(secretKey);
        return totp.verify(code);
    }

    public boolean isOTP(String input){
        return input != null && input.matches("\\d{6}");
    }
}
