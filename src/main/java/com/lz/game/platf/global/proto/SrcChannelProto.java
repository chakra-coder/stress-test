// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src_channel.proto

package com.lz.game.platf.global.proto;

public final class SrcChannelProto {
  private SrcChannelProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public enum SrcChannel
      implements com.google.protobuf.ProtocolMessageEnum {
    OFFICIAL(0, 1),
    MOBILE_91(1, 2),
    TONG_BU_TUI(2, 3),
    DANG_LE(3, 4),
    UC(4, 5),
    ;
    
    public static final int OFFICIAL_VALUE = 1;
    public static final int MOBILE_91_VALUE = 2;
    public static final int TONG_BU_TUI_VALUE = 3;
    public static final int DANG_LE_VALUE = 4;
    public static final int UC_VALUE = 5;
    
    
    public final int getNumber() { return value; }
    
    public static SrcChannel valueOf(int value) {
      switch (value) {
        case 1: return OFFICIAL;
        case 2: return MOBILE_91;
        case 3: return TONG_BU_TUI;
        case 4: return DANG_LE;
        case 5: return UC;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<SrcChannel>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<SrcChannel>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<SrcChannel>() {
            public SrcChannel findValueByNumber(int number) {
              return SrcChannel.valueOf(number);
            }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return SrcChannelProto.getDescriptor().getEnumTypes().get(0);
    }
    
    private static final SrcChannel[] VALUES = {
      OFFICIAL, MOBILE_91, TONG_BU_TUI, DANG_LE, UC, 
    };
    
    public static SrcChannel valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    
    private final int index;
    private final int value;
    
    private SrcChannel(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    // @@protoc_insertion_point(enum_scope:proto.SrcChannel)
  }
  
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\021src_channel.proto\022\005proto*O\n\nSrcChannel" +
      "\022\014\n\010OFFICIAL\020\001\022\r\n\tMOBILE_91\020\002\022\017\n\013TONG_BU" +
      "_TUI\020\003\022\013\n\007DANG_LE\020\004\022\006\n\002UC\020\005B3\n\036com.lz.ga" +
      "me.platf.global.protoB\017SrcChannelProtoH\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
