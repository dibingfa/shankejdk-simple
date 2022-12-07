/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
 */

//#define USE_ERROR
//#define USE_TRACE

#if USE_PLATFORM_MIDI_IN == TRUE

#include "PLATFORM_API_MacOSX_MidiUtils.h"

char* MIDI_IN_GetErrorStr(INT32 err) {
    return (char *) MIDI_Utils_GetErrorMsg((int) err);
}


INT32 MIDI_IN_GetNumDevices() {
    return MIDI_Utils_GetNumDevices(MIDI_IN);
}


INT32 MIDI_IN_GetDeviceName(INT32 deviceID, char *name, UINT32 nameLength) {
    return MIDI_Utils_GetDeviceName(MIDI_IN, deviceID, name, nameLength);
}


INT32 MIDI_IN_GetDeviceVendor(INT32 deviceID, char *name, UINT32 nameLength) {
    return MIDI_Utils_GetDeviceVendor(MIDI_IN, deviceID, name, nameLength);
}


INT32 MIDI_IN_GetDeviceDescription(INT32 deviceID, char *name, UINT32 nameLength) {
    return MIDI_Utils_GetDeviceDescription(MIDI_IN, deviceID, name, nameLength);
}


INT32 MIDI_IN_GetDeviceVersion(INT32 deviceID, char *name, UINT32 nameLength) {
    return MIDI_Utils_GetDeviceVersion(MIDI_IN, deviceID, name, nameLength);
}


INT32 MIDI_IN_OpenDevice(INT32 deviceID, MidiDeviceHandle** handle) {
    TRACE0("MIDI_IN_OpenDevice\n");
    return
        MIDI_Utils_OpenDevice(MIDI_IN, deviceID, (MacMidiDeviceHandle**) handle,
                              MIDI_IN_MESSAGE_QUEUE_SIZE,
                              MIDI_IN_LONG_QUEUE_SIZE,
                              MIDI_IN_LONG_MESSAGE_SIZE);
}


INT32 MIDI_IN_CloseDevice(MidiDeviceHandle* handle) {
    TRACE0("MIDI_IN_CloseDevice\n");
    return MIDI_Utils_CloseDevice((MacMidiDeviceHandle*) handle);
}


INT32 MIDI_IN_StartDevice(MidiDeviceHandle* handle) {
    TRACE0("MIDI_IN_StartDevice\n");
    return MIDI_Utils_StartDevice((MacMidiDeviceHandle*) handle);
}


INT32 MIDI_IN_StopDevice(MidiDeviceHandle* handle) {
    TRACE0("MIDI_IN_StopDevice\n");
    return MIDI_Utils_StopDevice((MacMidiDeviceHandle*) handle);
}

INT64 MIDI_IN_GetTimeStamp(MidiDeviceHandle* handle) {
    return MIDI_Utils_GetTimeStamp((MacMidiDeviceHandle*) handle);
}


/* read the next message from the queue */
MidiMessage* MIDI_IN_GetMessage(MidiDeviceHandle* handle) {
    if (handle == NULL) {
        return NULL;
    }
    while (handle->queue != NULL && handle->platformData != NULL) {
        MidiMessage* msg = MIDI_QueueRead(handle->queue);
        if (msg != NULL) {
            //fprintf(stdout, "GetMessage returns index %d\n", msg->data.l.index); fflush(stdout);
            return msg;
        }
        TRACE0("MIDI_IN_GetMessage: before waiting\n");
        handle->isWaiting = TRUE;
        MIDI_WaitOnConditionVariable(handle->platformData, handle->queue->lock);
        handle->isWaiting = FALSE;
        TRACE0("MIDI_IN_GetMessage: waiting finished\n");
    }
    return NULL;
}


void MIDI_IN_ReleaseMessage(MidiDeviceHandle* handle, MidiMessage* msg) {
    if (handle == NULL || handle->queue == NULL) {
        return;
    }
    MIDI_QueueRemove(handle->queue, TRUE /*onlyLocked*/);
}

#endif /* USE_PLATFORM_MIDI_IN */
