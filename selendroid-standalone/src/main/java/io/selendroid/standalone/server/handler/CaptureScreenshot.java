/*
 * Copyright 2012-2014 eBay Software Foundation and selendroid committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.selendroid.standalone.server.handler;

import io.selendroid.server.common.*;
import io.selendroid.server.common.http.*;
import io.selendroid.standalone.exceptions.*;
import io.selendroid.standalone.server.*;
import org.apache.commons.codec.binary.*;
import org.json.*;

import java.util.logging.*;

public class CaptureScreenshot extends BaseSelendroidStandaloneHandler {
  private static final Logger log = Logger.getLogger(CaptureScreenshot.class.getName());

  public CaptureScreenshot(String mappedUri) {
    super(mappedUri);
  }

  @Override
  public Response handleRequest(HttpRequest request, JSONObject payload) throws JSONException {
    byte[] rawPng;
    try {
      rawPng = getSelendroidDriver(request).takeScreenshot(getSessionId(request));
    } catch (AndroidDeviceException e) {
      log.log(Level.SEVERE, "Cannot take screenshot", e);
      return new SelendroidResponse(getSessionId(request), StatusCode.UNKNOWN_ERROR, e);
    }
    String base64Png = Base64.encodeBase64String(rawPng);

    return new SelendroidResponse(getSessionId(request), base64Png);
  }
}
