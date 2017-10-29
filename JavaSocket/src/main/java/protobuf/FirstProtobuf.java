// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: test.proto

package protobuf;

public final class FirstProtobuf {
	private FirstProtobuf() {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
	}

	public interface testBufOrBuilder extends com.google.protobuf.MessageOrBuilder {

		// required int32 ID = 1;
		/**
		 * <code>required int32 ID = 1;</code>
		 */
		boolean hasID();

		/**
		 * <code>required int32 ID = 1;</code>
		 */
		int getID();

		// required string Url = 2;
		/**
		 * <code>required string Url = 2;</code>
		 */
		boolean hasUrl();

		/**
		 * <code>required string Url = 2;</code>
		 */
		java.lang.String getUrl();

		/**
		 * <code>required string Url = 2;</code>
		 */
		com.google.protobuf.ByteString getUrlBytes();
	}

	/**
	 * Protobuf type {@code protobuf.testBuf}
	 */
	public static final class testBuf extends com.google.protobuf.GeneratedMessage implements testBufOrBuilder {
		// Use testBuf.newBuilder() to construct.
		private testBuf(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
			super(builder);
			this.unknownFields = builder.getUnknownFields();
		}

		private testBuf(boolean noInit) {
			this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance();
		}

		private static final testBuf defaultInstance;

		public static testBuf getDefaultInstance() {
			return defaultInstance;
		}

		public testBuf getDefaultInstanceForType() {
			return defaultInstance;
		}

		private final com.google.protobuf.UnknownFieldSet unknownFields;

		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
			return this.unknownFields;
		}

		private testBuf(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
			initFields();
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 8: {
						bitField0_ |= 0x00000001;
						iD_ = input.readInt32();
						break;
					}
					case 18: {
						bitField0_ |= 0x00000002;
						url_ = input.readBytes();
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
			} finally {
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}

		public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
			return protobuf.FirstProtobuf.internal_static_protobuf_testBuf_descriptor;
		}

		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
			return protobuf.FirstProtobuf.internal_static_protobuf_testBuf_fieldAccessorTable.ensureFieldAccessorsInitialized(protobuf.FirstProtobuf.testBuf.class, protobuf.FirstProtobuf.testBuf.Builder.class);
		}

		public static com.google.protobuf.Parser<testBuf> PARSER = new com.google.protobuf.AbstractParser<testBuf>() {
			public testBuf parsePartialFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
				return new testBuf(input, extensionRegistry);
			}
		};

		@java.lang.Override
		public com.google.protobuf.Parser<testBuf> getParserForType() {
			return PARSER;
		}

		private int bitField0_;
		// required int32 ID = 1;
		public static final int ID_FIELD_NUMBER = 1;
		private int iD_;

		/**
		 * <code>required int32 ID = 1;</code>
		 */
		public boolean hasID() {
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}

		/**
		 * <code>required int32 ID = 1;</code>
		 */
		public int getID() {
			return iD_;
		}

		// required string Url = 2;
		public static final int URL_FIELD_NUMBER = 2;
		private java.lang.Object url_;

		/**
		 * <code>required string Url = 2;</code>
		 */
		public boolean hasUrl() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}

		/**
		 * <code>required string Url = 2;</code>
		 */
		public java.lang.String getUrl() {
			java.lang.Object ref = url_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					url_ = s;
				}
				return s;
			}
		}

		/**
		 * <code>required string Url = 2;</code>
		 */
		public com.google.protobuf.ByteString getUrlBytes() {
			java.lang.Object ref = url_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
				url_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}

		private void initFields() {
			iD_ = 0;
			url_ = "";
		}

		private byte memoizedIsInitialized = -1;

		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1)
				return isInitialized == 1;

			if (!hasID()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasUrl()) {
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}

		public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
			getSerializedSize();
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				output.writeInt32(1, iD_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				output.writeBytes(2, getUrlBytes());
			}
			getUnknownFields().writeTo(output);
		}

		private int memoizedSerializedSize = -1;

		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1)
				return size;

			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, iD_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				size += com.google.protobuf.CodedOutputStream.computeBytesSize(2, getUrlBytes());
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}

		private static final long serialVersionUID = 0L;

		@java.lang.Override
		protected java.lang.Object writeReplace() throws java.io.ObjectStreamException {
			return super.writeReplace();
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(java.io.InputStream input) throws java.io.IOException {
			return PARSER.parseFrom(input);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}

		public static protobuf.FirstProtobuf.testBuf parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input);
		}

		public static protobuf.FirstProtobuf.testBuf parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
			return PARSER.parseDelimitedFrom(input, extensionRegistry);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
			return PARSER.parseFrom(input);
		}

		public static protobuf.FirstProtobuf.testBuf parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
			return PARSER.parseFrom(input, extensionRegistry);
		}

		public static Builder newBuilder() {
			return Builder.create();
		}

		public Builder newBuilderForType() {
			return newBuilder();
		}

		public static Builder newBuilder(protobuf.FirstProtobuf.testBuf prototype) {
			return newBuilder().mergeFrom(prototype);
		}

		public Builder toBuilder() {
			return newBuilder(this);
		}

		@java.lang.Override
		protected Builder newBuilderForType(com.google.protobuf.GeneratedMessage.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}

		/**
		 * Protobuf type {@code protobuf.testBuf}
		 */
		public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements protobuf.FirstProtobuf.testBufOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
				return protobuf.FirstProtobuf.internal_static_protobuf_testBuf_descriptor;
			}

			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
				return protobuf.FirstProtobuf.internal_static_protobuf_testBuf_fieldAccessorTable.ensureFieldAccessorsInitialized(protobuf.FirstProtobuf.testBuf.class, protobuf.FirstProtobuf.testBuf.Builder.class);
			}

			// Construct using protobuf.FirstProtobuf.testBuf.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}

			private Builder(com.google.protobuf.GeneratedMessage.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
				}
			}

			private static Builder create() {
				return new Builder();
			}

			public Builder clear() {
				super.clear();
				iD_ = 0;
				bitField0_ = (bitField0_ & ~0x00000001);
				url_ = "";
				bitField0_ = (bitField0_ & ~0x00000002);
				return this;
			}

			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}

			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
				return protobuf.FirstProtobuf.internal_static_protobuf_testBuf_descriptor;
			}

			public protobuf.FirstProtobuf.testBuf getDefaultInstanceForType() {
				return protobuf.FirstProtobuf.testBuf.getDefaultInstance();
			}

			public protobuf.FirstProtobuf.testBuf build() {
				protobuf.FirstProtobuf.testBuf result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			public protobuf.FirstProtobuf.testBuf buildPartial() {
				protobuf.FirstProtobuf.testBuf result = new protobuf.FirstProtobuf.testBuf(this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
					to_bitField0_ |= 0x00000001;
				}
				result.iD_ = iD_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
					to_bitField0_ |= 0x00000002;
				}
				result.url_ = url_;
				result.bitField0_ = to_bitField0_;
				onBuilt();
				return result;
			}

			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof protobuf.FirstProtobuf.testBuf) {
					return mergeFrom((protobuf.FirstProtobuf.testBuf) other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(protobuf.FirstProtobuf.testBuf other) {
				if (other == protobuf.FirstProtobuf.testBuf.getDefaultInstance())
					return this;
				if (other.hasID()) {
					setID(other.getID());
				}
				if (other.hasUrl()) {
					bitField0_ |= 0x00000002;
					url_ = other.url_;
					onChanged();
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}

			public final boolean isInitialized() {
				if (!hasID()) {

					return false;
				}
				if (!hasUrl()) {

					return false;
				}
				return true;
			}

			public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
				protobuf.FirstProtobuf.testBuf parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (protobuf.FirstProtobuf.testBuf) e.getUnfinishedMessage();
					throw e;
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}

			private int bitField0_;

			// required int32 ID = 1;
			private int iD_;

			/**
			 * <code>required int32 ID = 1;</code>
			 */
			public boolean hasID() {
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}

			/**
			 * <code>required int32 ID = 1;</code>
			 */
			public int getID() {
				return iD_;
			}

			/**
			 * <code>required int32 ID = 1;</code>
			 */
			public Builder setID(int value) {
				bitField0_ |= 0x00000001;
				iD_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required int32 ID = 1;</code>
			 */
			public Builder clearID() {
				bitField0_ = (bitField0_ & ~0x00000001);
				iD_ = 0;
				onChanged();
				return this;
			}

			// required string Url = 2;
			private java.lang.Object url_ = "";

			/**
			 * <code>required string Url = 2;</code>
			 */
			public boolean hasUrl() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			/**
			 * <code>required string Url = 2;</code>
			 */
			public java.lang.String getUrl() {
				java.lang.Object ref = url_;
				if (!(ref instanceof java.lang.String)) {
					java.lang.String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
					url_ = s;
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <code>required string Url = 2;</code>
			 */
			public com.google.protobuf.ByteString getUrlBytes() {
				java.lang.Object ref = url_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
					url_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <code>required string Url = 2;</code>
			 */
			public Builder setUrl(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				url_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required string Url = 2;</code>
			 */
			public Builder clearUrl() {
				bitField0_ = (bitField0_ & ~0x00000002);
				url_ = getDefaultInstance().getUrl();
				onChanged();
				return this;
			}

			/**
			 * <code>required string Url = 2;</code>
			 */
			public Builder setUrlBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				url_ = value;
				onChanged();
				return this;
			}

			// @@protoc_insertion_point(builder_scope:protobuf.testBuf)
		}

		static {
			defaultInstance = new testBuf(true);
			defaultInstance.initFields();
		}

		// @@protoc_insertion_point(class_scope:protobuf.testBuf)
	}

	private static com.google.protobuf.Descriptors.Descriptor internal_static_protobuf_testBuf_descriptor;
	private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_protobuf_testBuf_fieldAccessorTable;

	public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
		return descriptor;
	}

	private static com.google.protobuf.Descriptors.FileDescriptor descriptor;
	static {
		java.lang.String[] descriptorData = { "\n\ntest.proto\022\010protobuf\"\"\n\007testBuf\022\n\n\002ID\030" + "\001 \002(\005\022\013\n\003Url\030\002 \002(\tB\031\n\010protobufB\rFirstPro" + "tobuf" };
		com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
			public com.google.protobuf.ExtensionRegistry assignDescriptors(com.google.protobuf.Descriptors.FileDescriptor root) {
				descriptor = root;
				internal_static_protobuf_testBuf_descriptor = getDescriptor().getMessageTypes().get(0);
				internal_static_protobuf_testBuf_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_protobuf_testBuf_descriptor, new java.lang.String[] { "ID", "Url", });
				return null;
			}
		};
		com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {}, assigner);
	}

	// @@protoc_insertion_point(outer_class_scope)
}
