## How to use BhapticsManager

### Access BhapticsManager
```
BhapticsManager bhapticsManager = BhapticsModule.getBhapticsManager();
```


### Define callback to get current device status
```
        bhapticsManager.addBhapticsManageCallback(new BhapticsManagerCallback() {
            @Override
            public void onDeviceUpdate(List<BhapticsDevice> list) {
                adapter.onChangeListUpdate(list);
            }

            @Override
            public void onScanStatusChange(boolean b) {
                if (b) {
                    scanButton.setText("Scanning");
                } else {
                    scanButton.setText("Scan");
                }
            }

            @Override
            public void onChangeResponse() { }

            @Override
            public void onConnect(String s) { }

            @Override
            public void onDisconnect(String s) { }
        });
```

### Scan
```
// start scan
bhapticsManager.scan();

// stop scan
bhapticsManager.stopScan();
```


### Pair/Unpair
```
bhapticsManager.unpair(bhapticsDevice.getAddress());
bhapticsManager.pair(bhapticsDevice.getAddress());
```

### Toggle Position (For tactosy arm, hand, foot)
```
bhapticsManager.togglePosition(bhapticsDevice.getAddress());
bhapticsManager.changePosition(bhapticsDevice.getAddress(), PositionType.ForearmL);
```

### Ping
```
bhapticsManager.ping(bhapticsDevice.getAddress());
bhapticsManager.pingAll();
```
