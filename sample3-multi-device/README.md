### Change index
* Index should be 0, 1, 2, 3, 4
```
int newIndex = device.getIndex() + 1;
if (newIndex >= 5) {
    newIndex = 0;
}
App.getBhapticsManager().changeIndex(device.getAddress(), newIndex);
```

### Get HapticPlayer with Index
```
nt index = 1;
HapticPlayer player = App.getPlayer(index);

if (!player.isRegistered(key1)) {
    player.registerProject(key1, value1);
}
player.submitRegistered(key1,
        key1, 0.5f, 2f, 359, 0);
```