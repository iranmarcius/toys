package toys.records;

public record BinaryContentMetadata(
  byte[] content,
  long size,
  String contentType,
  String contentEncoding
) {
}
