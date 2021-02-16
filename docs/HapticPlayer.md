## How to use HapticPlayer

### Access HapticPlayer
```
HapticPlayer hapticPlayer = BhapticsModule.getHapticPlayer();
```


### Generate haptic pattern with tact file
* [About tact file](https://github.com/bhaptics/TactUnrealEngine4/wiki/Getting-Started-(From-1.5.0)#Appendix)
* You can use example tact files [here](https://github.com/bhaptics/haptic-guide)

```
// register tact file before using it. 
// You need to register just once.
String key = "key1";

// tact file String 
// check out how to read file from this link
// https://github.com/bhaptics/tact-android/blob/update/app/src/main/java/com/bhaptics/bhapticsandroid/utils/FileUtils.java
String tactFileString = "";

hapticPlayer.registerProject(fileKey, fileContent);


// play tact file with simple way.
hapticPlayer.submitRegistered(key);

// alt key used to generate same tact file at the same time
String altKey = "altKey"; // it can be randomly generated String or use object pooling.

// intensityMultiplier: feedback intensity will be multiplied by this value (0.2, 5)
float intensityMultiplier = 1f;

// durationMultiplier: feedback duration will be multiplied by this value (0.2, 5)
float durationMultiplier = 1f; 

// These two options are used only for the vest to generate directional haptic patterns.
// xOffsetAngle: horizontal angle (0, 360)
float xOffsetAngle = 0f; //  (0, 360)
// yOffset vertical offset
float yOffset = 0; // (-0.5, 0.5)


 hapticPlayer.submitRegistered(
                            key,
                            altKey,
                            intensityMultiplier, durationMultiplier,
                            xOffsetAngle  , yOffset
                            );
```


### Calculate angle
* [Unity Example](https://github.com/bhaptics/haptic-library/blob/master/samples/unity-plugin/Assets/Bhaptics/SDK/Scripts/BhapticsUtils.cs);
```
public static float Angle(Vector3 fwd, Vector3 targetDir)
```

* [UE Example](https://github.com/bhaptics/TactUnrealEngine4/issues/47)



### Turn off haptic patterns
```
// to turnOff just one haptic patterns with key
hapticPlayer.turnOff(fileKey);

// to turn off all the haptic patterns playing
hapticPlayer.turnOffAll();
```


### Generate haptic pattern with its motor index and intensity: Dots
```
// Set 0 th motor with intensity 100 and 2nd motor with intensity 50
// The index ranges from 0 to 19 for the vest devices, 
// 0 to 5 for the tactal and tactosy arm devices, 
// and 0 to 2 for other devices, and moves from left to right.

DotPoint dotPoint1 = new DotPoint(0, 100);
DotPoint dotPoint2 = new DotPoint(2, 50);


// key can be used to turn off if you want.
String key1 = "test";
String key2 = "test";
PositionType position1 = PositionType.ForearmL;
PositionType position2 = PositionType.VestFront;

// duration(ms) 1 second.
int duration = 1000; 

// Turning on the motors
hapticPlayer.submitDot(key1, 
        position1, Arrays.asList(dotPoint1, dotPoint2), duration);
hapticPlayer.submitDot(key2, 
        position2, Arrays.asList(dotPoint1, dotPoint2), duration);
```

### Generate haptic pattern with its logical x-y plane and intensity: Paths
```
String key1 = "key1";
PositionType position1 = PositionType.VestBack;

// x (0~1): left side is 0 and right side is 1.
// y (0~1): top is 0 and bottom is 1.
// Center of the plane with intensity 100
float x = 0.5f;
float y = 0.5f;
float intensity = 100; 
PathPoint p1 = new PathPoint(x, y, intensity);

// duration(ms) 1 second.
int duration = 1000; 

hapticPlayer.submitPath(key1, position1,
        Arrays.asList(p1), duration);

```


