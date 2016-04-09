package verifytoken;

/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.jackson.JacksonFactory;

/**
 * Simple server to demonstrate token verification.
 *
 * @author cartland@google.com (Chris Cartland)
 */
public class Verify {
  /** Replace this with the client ID you got from the Google APIs console.   */
  private static final String CLIENT_ID = "495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com";
  /** Optionally replace this with your application's name.  */
  private static final String APPLICATION_NAME = "haiHome? Token Verification";
  /** Default HTTP transport to use to make HTTP requests.  */
  private static final HttpTransport TRANSPORT = new NetHttpTransport();
  /** Default JSON factory to use to deserialize JSON.   */
  private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

  /* Restituisce un array di due stringhe o null (in caso di autenticazione fallita)
   * res[0] : userId
   * res[1] : userEmail  */
  public static String[] getUserCredentials(String idToken, String accessToken) {
      VerificationResponse tokenStatus = verify(idToken, accessToken);
      TokenStatus idTokenStatus = tokenStatus.id_token_status, 
                  accessTokenStatus = tokenStatus.access_token_status; 
      String[] res = null;
      
      if (idTokenStatus.valid && accessTokenStatus.valid)
          if (idTokenStatus.gplus_id.equals(accessTokenStatus.gplus_id)) {
              res = new String[2];
              res[0] = idTokenStatus.gplus_id; 
              res[1] = idTokenStatus.gplus_email;
          }
      
      return res;
  }
  
  private static VerificationResponse verify(String idToken, String accessToken) {

        TokenStatus idStatus = new TokenStatus();
        if (idToken != null) {
          // Check that the ID Token is valid.

          Checker checker = new Checker(new String[]{CLIENT_ID}, CLIENT_ID);
          GoogleIdToken.Payload jwt = checker.check(idToken);

          if (jwt == null) {
            // This is not a valid token.
            idStatus.setValid(false);
            idStatus.setId("");
            idStatus.setMessage("Invalid ID Token.");       
          } 
          else {
            idStatus.setValid(true);
            idStatus.setId((String)jwt.get("sub")); 
            idStatus.setEmail((String)jwt.get("email"));
            idStatus.setMessage("ID Token is valid.");
          }
        } else {
          idStatus.setMessage("ID Token not provided");
        }

        TokenStatus accessStatus = new TokenStatus();
        if (accessToken != null) {
          // Check that the Access Token is valid.
          try {
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
            Oauth2 oauth2 = new Oauth2.Builder(TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
            Tokeninfo tokenInfo = oauth2.tokeninfo().setAccessToken(accessToken).execute();
            
            if (!tokenInfo.getIssuedTo().equals(CLIENT_ID)) {
              // This is not meant for this app. It is VERY important to check
              // the client ID in order to prevent man-in-the-middle attacks.
              accessStatus.setValid(false);
              accessStatus.setId("");
              accessStatus.setMessage("Access Token not meant for this app.");
            } else {
              accessStatus.setValid(true);
              accessStatus.setId(tokenInfo.getUserId());
              accessStatus.setMessage("Access Token is valid.");
            }
          } catch (IOException e) {
            accessStatus.setValid(false);
            accessStatus.setId("");
            accessStatus.setMessage("Invalid Access Token.");
          }
        } else {
          accessStatus.setMessage("Access Token not provided");
        }

        return new VerificationResponse(idStatus, accessStatus); 
  }


  /** JSON representation of a token's status.  */
  public static class TokenStatus {
    public boolean valid;
    public String gplus_id;     
    public String gplus_email;
    public String message;


    public TokenStatus() {
      valid = false;
      gplus_id = "";
      gplus_email = "";
      message = "";
    }

    public void setValid(boolean v) {
      this.valid = v;
    }

    public void setId(String gplus_id) {
      this.gplus_id = gplus_id;
    }
    
    public void setEmail(String gplus_mail) {
        this.gplus_email = gplus_mail;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }

  /** JSON response to verification request. */
  public static class VerificationResponse {
    public TokenStatus id_token_status;
    public TokenStatus access_token_status;

    private VerificationResponse(TokenStatus _id_token_status, TokenStatus _access_token_status) {
      this.id_token_status = _id_token_status;
      this.access_token_status = _access_token_status;
    }
  }
}

