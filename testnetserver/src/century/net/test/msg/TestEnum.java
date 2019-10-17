// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: core.proto

package century.net.test.msg;

/**
 * Protobuf enum {@code century.net.test.msg.TestEnum}
 */
public enum TestEnum
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>SUCCESS = 0;</code>
   */
  SUCCESS(0),
  /**
   * <code>TO_SYSCTL = 1;</code>
   */
  TO_SYSCTL(1),
  /**
   * <code>TO_DISCONNECT = 2;</code>
   */
  TO_DISCONNECT(2),
  /**
   * <code>RR_NO_TIMELINE = 3;</code>
   */
  RR_NO_TIMELINE(3),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>SUCCESS = 0;</code>
   */
  public static final int SUCCESS_VALUE = 0;
  /**
   * <code>TO_SYSCTL = 1;</code>
   */
  public static final int TO_SYSCTL_VALUE = 1;
  /**
   * <code>TO_DISCONNECT = 2;</code>
   */
  public static final int TO_DISCONNECT_VALUE = 2;
  /**
   * <code>RR_NO_TIMELINE = 3;</code>
   */
  public static final int RR_NO_TIMELINE_VALUE = 3;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static TestEnum valueOf(int value) {
    return forNumber(value);
  }

  public static TestEnum forNumber(int value) {
    switch (value) {
      case 0: return SUCCESS;
      case 1: return TO_SYSCTL;
      case 2: return TO_DISCONNECT;
      case 3: return RR_NO_TIMELINE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<TestEnum>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      TestEnum> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<TestEnum>() {
          public TestEnum findValueByNumber(int number) {
            return TestEnum.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return century.net.test.msg.TestMessage.getDescriptor().getEnumTypes().get(0);
  }

  private static final TestEnum[] VALUES = values();

  public static TestEnum valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private TestEnum(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:century.net.test.msg.TestEnum)
}

