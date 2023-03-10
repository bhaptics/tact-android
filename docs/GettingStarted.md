## Getting Started
### bHaptics Developer Portal
* Before you start, you need to setup application
* https://www.notion.so/bhaptics/Create-haptic-events-using-bHaptics-Developer-Portal-b056c5a56e514afeb0ed436873dd87c6


### Initialize SdkRequestHandler
```
SdkRequestHandler requestHandler = new SdkRequestHandler(context);
requestHandler.initialize(appId, sdkKey, defaultSetting);
```

### Dispose when application ends
```
requestHandler.quit();
```

### play event
```
 requestHandler.play(appId);
```

### play event with options
```
 requestHandler.play(appId, eventName, intensity, duration, angleX, offsetY);
```

