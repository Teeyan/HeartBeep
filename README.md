# HeartBeep

Android Application targeted towards benefiting the elderly amongst other potential demographics. Sends a push notification to a user designated emergency contact if the user has not opened their phone in a certain period of time.

# Setup

Use of Node.js, Twilio, and ngrok for secure tunneling of local host ports. 

Use of Android Volley for sending POST requests to Node.js server. 

Setting up HTTP server with npm:
At top level of server directory : `npm install`
Run `ngrok` with port you want to tunnel.
Configure server.js to use the port you tunneled.
Type `node server.js`.

# Configuration
Created with Android Studio v2.2.3
Created with Node.js v4.6.2
Created with Android Volley : https://github.com/google/volley <--- Literally Saved our Lives 

# Issues - Potential Future Features

**Local Password** : Keep settings secure for individuals who may have mood swings or unpredictable behavior. Loved ones can ensure that the primary benefactor of HeartBeep will not accidentally or intentionally override settings.

**Voice Recognition** : Original Feature Intended but scoped for time constraints. Google Cloud Voice Recognition API implementation for 
situations where the benefactor of HeartBeep may be immobilized or unable to reach their mobile device. In addition, instead of logging 
activity through unlocking the phone, presence can be recognized by a preset phrase.

**Calling Emergency Contacts** : Use of Twilio to call the designated emergency contact with an automated voice message before sending out an SMS. Can be argued to be more effective and more likely to be noticed/responded to.


# Copyright and License Information

   Copyright 2017 DjJerryJ

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

