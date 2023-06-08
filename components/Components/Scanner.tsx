/* eslint-disable react-native/no-inline-styles */
import React from 'react';
import QRCodeScanner from 'react-native-qrcode-scanner';
import { View, SafeAreaView } from 'react-native';

import { useTheme } from '@react-navigation/native';
import { BarCodeReadEvent } from 'react-native-camera';
import RegText from './RegText';
import Button from './Button';

type ScannerProps = {
  onRead: (e: BarCodeReadEvent) => void;
  doCancel: () => void;
  title: string;
  button: string;
};

const Scanner: React.FunctionComponent<ScannerProps> = ({ onRead, doCancel, title, button }) => {
  const { colors } = useTheme();
  return (
    <SafeAreaView
      style={{
        width: '100%',
        height: '100%',
      }}>
      <QRCodeScanner
        showMarker={true}
        onRead={onRead}
        reactivate={true}
        containerStyle={{
          backgroundColor: colors.background,
        }}
        cameraContainerStyle={{
          overflow: 'hidden',
        }}
        topContent={
          <View>
            <View>
              <RegText>{title}</RegText>
            </View>
          </View>
        }
        bottomContent={
          <View>
            <View style={{ width: '100%' }}>
              <Button testID="scan.cancel" type="Secondary" title={button} onPress={doCancel} />
            </View>
          </View>
        }
      />
    </SafeAreaView>
  );
};

export default Scanner;
