// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service1.proto

package ds.service1;

public final class Service1Impl {
  private Service1Impl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service1_ContaminateLimit_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service1_ContaminateLimit_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service1_ContaminateCheck_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service1_ContaminateCheck_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service1_Pipe_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service1_Pipe_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service1_Pipe_ContaminateAmountEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service1_Pipe_ContaminateAmountEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016service1.proto\022\010service1\"!\n\020Contaminat" +
      "eLimit\022\r\n\005limit\030\031 \001(\005\"!\n\020ContaminateChec" +
      "k\022\r\n\005level\030\002 \001(\005\"\332\001\n\004Pipe\022\016\n\006pipeId\030\002 \001(" +
      "\005\022*\n\npipeStatus\030\001 \001(\0162\026.service1.PipeShu" +
      "tdown\022@\n\021contaminateAmount\030\005 \003(\0132%.servi" +
      "ce1.Pipe.ContaminateAmountEntry\032T\n\026Conta" +
      "minateAmountEntry\022\013\n\003key\030\001 \001(\005\022)\n\005value\030" +
      "\002 \001(\0132\032.service1.ContaminateLimit:\0028\001*$\n" +
      "\014PipeShutdown\022\010\n\004OPEN\020\000\022\n\n\006CLOSED\020\0012M\n\010S" +
      "ervice1\022A\n\005check\022\032.service1.ContaminateL" +
      "imit\032\032.service1.ContaminateCheck\"\000B\035\n\013ds" +
      ".service1B\014Service1ImplP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
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
    internal_static_service1_ContaminateLimit_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_service1_ContaminateLimit_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service1_ContaminateLimit_descriptor,
        new java.lang.String[] { "Limit", });
    internal_static_service1_ContaminateCheck_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_service1_ContaminateCheck_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service1_ContaminateCheck_descriptor,
        new java.lang.String[] { "Level", });
    internal_static_service1_Pipe_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_service1_Pipe_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service1_Pipe_descriptor,
        new java.lang.String[] { "PipeId", "PipeStatus", "ContaminateAmount", });
    internal_static_service1_Pipe_ContaminateAmountEntry_descriptor =
      internal_static_service1_Pipe_descriptor.getNestedTypes().get(0);
    internal_static_service1_Pipe_ContaminateAmountEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service1_Pipe_ContaminateAmountEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}