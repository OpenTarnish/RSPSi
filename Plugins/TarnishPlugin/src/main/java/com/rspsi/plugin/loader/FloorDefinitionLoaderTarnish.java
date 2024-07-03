package com.rspsi.plugin.loader;

import com.displee.cache.index.archive.Archive;

import com.jagex.cache.def.Floor;
import com.jagex.cache.loader.floor.FloorDefinitionLoader;
import com.jagex.cache.loader.floor.FloorType;
import com.jagex.io.Buffer;

public class FloorDefinitionLoaderTarnish extends FloorDefinitionLoader {

	private Floor[] overlays;
	private Floor[] underlays;

	@Override
	public void init(Archive archive) {
		initUnderlays(archive);
		initOverlays(archive);
	}

	private void initUnderlays(final Archive archive) {
		final Buffer buffer = new Buffer(archive.file("underlays.dat"));

		final int highestFileId = buffer.readUShort();
		System.out.println("underlayAmount=" + highestFileId);
		underlays = new Floor[highestFileId + 1];

		for (int i = 0; i <= highestFileId; i++) {
			final int id = buffer.readUShort();
			if (id == -1 || id == 65535) continue;

			Floor floorDefinition = underlays[id];
			if (floorDefinition == null) {
				floorDefinition = underlays[id] = new Floor();
			}

			final int length = buffer.readUShort();
			final byte[] data = new byte[length];
			buffer.readData(data, 0, length);

			readValuesUnderlay(floorDefinition, new Buffer(data));
			floorDefinition.generateHsl();

			if (id >= highestFileId) break;
		}
	}

	private void initOverlays(final Archive archive) {
		final Buffer buffer = new Buffer(archive.file("overlays.dat"));

		final int highestFileId = buffer.readUShort();
		System.out.println("overlayAmount="+highestFileId);
		overlays = new Floor[highestFileId + 1];

		for (int i = 0; i <= highestFileId; i++) {
			final int id = buffer.readUShort();
			if (id == -1 || id == 65535) continue;

			Floor floorDefinition = overlays[id];
			if (floorDefinition == null) {
				floorDefinition = overlays[id] = new Floor();
			}

			final int length = buffer.readUShort();
			final byte[] data = new byte[length];
			buffer.readData(data, 0, length);

			readValuesOverlay(floorDefinition, new Buffer(data));
			floorDefinition.generateHsl();

			if (id >= highestFileId) break;
		}
	}

	private void readValuesUnderlay(Floor floor, Buffer buffer) {
		for (;;) {
			int opcode = buffer.readUByte();
			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				floor.setRgb((buffer.readUByte() << 16) + (buffer.readUByte() << 8) + buffer.readUByte());
			} else {
				System.out.println("Error unrecognised underlay code: " + opcode);
			}
		}
	}

	private void readValuesOverlay(Floor floor, Buffer buffer) {
		for (;;) {
			int opcode = buffer.readUByte();
			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				floor.setRgb((buffer.readUByte() << 16) + (buffer.readUByte() << 8) + buffer.readUByte());
			} else if (opcode == 2) {
				floor.setTexture(buffer.readUByte());
			} else if (opcode == 5) {
				floor.setShadowed(false);
			} else if (opcode == 7) {
				floor.setAnotherRgb((buffer.readUByte() << 16) + (buffer.readUByte() << 8) + buffer.readUByte());
			} else {
				System.out.println("Error unrecognised overlay code: " + opcode);
			}
		}
	}

//	private void initOverlays(Archive archive) {
//		Buffer buffer = new Buffer(archive.file("overlays.dat").getData());
//
//		int highestFileId = buffer.readUShort();
//		System.out.println("Overlay Floors Loaded: " + highestFileId);
//		overlays = new Floor[highestFileId + 1];
//		for (int i = 0; i <= highestFileId; i++) {
//			final int id = buffer.readUShort();
//			if (id == -1 || id == 65535) continue;
//			if (id >= highestFileId) break;
//
//			if (overlays[id] == null) {
//				overlays[id] = new Floor();
//			}
//
//			final int length = buffer.readUShort();
//			final byte[] data = new byte[length];
//			buffer.readData(data, 0, length);
//			overlays[i] = decodeOverlay(buffer);
//			overlays[i].generateHsl();
//			if (id >= highestFileId) break;
//		}
//	}
//
//	private void initUnderlays(Archive archive) {
//
//		Buffer buffer = new Buffer(archive.file("underlays.dat").getData());
//
//		int highestFileId = buffer.readUShort();
//		System.err.println("Underlay Floors Loaded: " + highestFileId);
//		underlays = new Floor[highestFileId + 1];
//		for (int i = 0; i <= highestFileId; i++) {
//			final int id = buffer.readUShort();
//			if (id == -1 || id == 65535) continue;
//			if (id >= highestFileId) break;
//
//			if (underlays[id] == null) {
//				underlays[id] = new Floor();
//			}
//
//			final int length = buffer.readUShort();
//			final byte[] data = new byte[length];
//			buffer.readData(data, 0, length);
//			underlays[i] = decodeUnderlay(buffer);
//			underlays[i].generateHsl();
//			if (id >= highestFileId) break;
//		}

//		int underlayAmount = buffer.getShort();
//		System.out.println("Underlay Floors Loaded: " + underlayAmount);
//		underlays = new Floor[underlayAmount];
//		for (int i = 0; i < underlayAmount; i++) {
//			underlays[i] = decodeUnderlay(buffer);
//			underlays[i].generateHsl();
//		}
//	}

	@Override
	public void init(byte[] data) {

	}
//
//	public Floor decodeUnderlay(Buffer buffer) {
//		Floor floor = new Floor();
//		while (true){
//			int opcode = buffer.readUByte();
//			if (opcode == 0) {
//				break;
//			} else if (opcode == 1) {
//				int rgb = (buffer.readUByte() << 16) + (buffer.readUByte() << 8) + buffer.readUByte();
//				floor.setRgb(rgb);
//			} else {
//				System.out.println("Error unrecognised underlay code: " + opcode);
//			}
//		}
//		return floor;
//	}
//
//	public Floor decodeOverlay(Buffer buffer) {
//		Floor floor = new Floor();
//		while (true) {
//			int opcode = buffer.readUByte();
//			if (opcode == 0) {
//				break;
//			} else if (opcode == 1) {
//				int rgb = (buffer.readUByte() << 16) + (buffer.readUByte() << 8) + buffer.readUByte();
//				floor.setRgb(rgb);
//			} else if (opcode == 2) {
//				int texture = buffer.readUByte();
//				floor.setTexture(texture);
//			} else if (opcode == 5) {
//				floor.setShadowed(false);
//			} else if (opcode == 7) {
//				int anotherRgb = (buffer.readUByte() << 16) + (buffer.readUByte() << 8) + buffer.readUByte();
//				floor.setAnotherRgb(anotherRgb);
//			} else {
//				System.out.println("Error unrecognised overlay code: " + opcode);
//			}
//		}
//		return floor;
//	}


	
	@Override
	public Floor getFloor(int id, FloorType type) {
		if(type == FloorType.OVERLAY)
			return overlays[id];
		else
			return underlays[id];
	}


	@Override
	public int getSize(FloorType type) {
		if(type == FloorType.OVERLAY)
			return overlays.length;
		else
			return underlays.length;
	}

	@Override
	public int count() {
		return 0;
	}

	@Override
	public Floor forId(int arg0) {
		return null;
	}


}
