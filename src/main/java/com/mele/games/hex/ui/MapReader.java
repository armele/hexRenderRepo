package com.mele.games.hex.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.mele.games.hex.IHexResident;
import com.mele.games.utils.GameException;

/**
 * TODO: [Long Term] Create the reverse of this class - the MapWriter.  This would allow users to graphically
 * design maps and then save the ASCII result for use in future games.
 * 
 * The MapReader class reads an ASCII file and uses it to create and populate
 * a hex array based on that file.  The file must be in the format below:
 * 
 * XY  XY  XY  XY
 *   XY  XY  XY
 * XY  XY  XY  XY
 *   XY  XY  XY
 * 
 * In this example a 7 hex wide by 2 hex tall map will be created, where each "X"
 * represents the type (or terrain) of the hex, and each "Y" represents a resident on that hex.
 * 
 * TODO: Support groups of residents.
 * 
 * @author Ayar
 *
 */
public class MapReader {
	protected static Logger log = LogManager.getLogger(MapReader.class);
	protected static HashMap<String, Class<?>> symbolType = new HashMap<String, Class<?>>();
	protected static HashMap<String, Class<?>> symbolResident = new HashMap<String, Class<?>>();
	protected static boolean autoregistered = false;
	
	/**
	 * @param mapFile
	 * @return
	 * @throws FileNotFoundException
	 */
	protected static HexArray initializeHexMap(File mapFile) throws FileNotFoundException {
		int columns = 0;
		double rows = 0;
		
		Scanner scanner = new Scanner(mapFile);
		
		// A bit unintuitive, but remember that in the ascii hex map the first
		// text row represents the first cell of each odd column, and the second
		// text row represents the first cell of each even column.  So to get the
		// total number of columns, you need to count the cells in both rows.
		// And since the HexArray is created by indicating the number of cells
		// in the first column, only every odd text row counts as a cell in the
		// first column (hence the addition of .5 per text row instead of 1).
		while (scanner.hasNext()) {
			rows = rows + .5;
			String output = scanner.nextLine();
			
			if (rows <= 1) {
				String[] cellTokens = output.trim().split("  ");
				columns = columns + cellTokens.length;
			}
		}
		
		HexArray hexMap = new HexArray(columns, (int)(rows + .5));
		scanner.close();
		
		return hexMap;
	}
	
	/**
	 * @param symbol
	 * @param type
	 */
	public static void registerTypeSymbol(String symbol, Class<?> type) {
		log.info("Registering " + (symbol == null ? "null" : symbol.toString()) + " to " + (type == null ? "null" : type.getName()));
		symbolType.put(symbol, type);
	}
	
	/**
	 * @param symbol
	 * @param resident
	 */
	public static void registerResidentSymbol(String symbol, Class<?> resident) {
		log.info("Registering " + (symbol == null ? "null" : symbol.toString()) + " to " + (resident == null ? "null" : resident.getName()));		
		symbolResident.put(symbol, resident);
	}	
	
	/**
	 * @param cell
	 * @param residentSymbol
	 */
	protected static void makeResident(HexCell cell, String residentSymbol) {
		try {
			Class<?> resClass = symbolResident.get(residentSymbol);
			if (resClass != null) {
				if (IHexResident.class.isAssignableFrom(resClass)) {
					IHexResident resident = (IHexResident)resClass.newInstance();
					cell.addResident(resident);
				} else {
					String msg = "Cannot assign resident for symbol '" + residentSymbol + "'. Resident class: " + resClass;
					log.error(msg);
					throw new GameException(msg);
				}
			} else {
				log.debug("No resident mapped to " + residentSymbol);
			}
		} catch (InstantiationException e) {
			log.error(GameException.fullExceptionInfo(e));
			throw new GameException("Could not instantiate a class for resident symbol " + residentSymbol, e);
		} catch (IllegalAccessException e) {
			log.error(GameException.fullExceptionInfo(e));
			throw new GameException("Could not access class for resident symbol " + residentSymbol, e);
		}		
	}
	
	/**
	 * @param cell
	 * @param residentSymbol
	 */
	protected static void makeType(HexCell cell, String typeSymbol) {
		try {
			Class<?> resClass = symbolType.get(typeSymbol);
			if (resClass != null) {
				if (ICellType.class.isAssignableFrom(resClass)) {
					ICellType type = (ICellType)resClass.newInstance();
					cell.setType(type);
					log.info("Setting cell type for " + cell + ": " + type);
				} else {
					String msg = "Cannot assign type for symbol '" + typeSymbol + "'.  Class is not assignable from ICellType: " + resClass;
					log.error(msg);
					throw new GameException(msg);
				}
			} else {
				log.debug("No type mapped to " + typeSymbol);
			}
		} catch (InstantiationException e) {
			log.error(GameException.fullExceptionInfo(e));
			throw new GameException("Could not instantiate a class for type symbol " + typeSymbol, e);
		} catch (IllegalAccessException e) {
			log.error(GameException.fullExceptionInfo(e));
			throw new GameException("Could not access class for type symbol " + typeSymbol, e);
		}		
	}	
	
	/**
	 * Responsible for examining the classpath for any objects annotated as being CellTypeMetadata, 
	 * or ResidentMetadata
	 * 
	 */
	protected static void autoregister() {
		log.debug("Autoregistering Cell Types and Residents...");
		Reflections reflections = new Reflections("");    
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(CellTypeMetadata.class);
		
		for (Class<?> type : classes) {
			CellTypeMetadata cellType = type.getAnnotation(CellTypeMetadata.class);
			
			if (ICellType.class.isAssignableFrom(type)) {
				registerTypeSymbol(cellType.symbol(), type);	
			} else {
				throw new GameException("A class which does not implement ICellType has been annotated with CellTypeMetadata: " + type.getName());
			}
		}		
		
		classes = reflections.getTypesAnnotatedWith(ResidentMetadata.class);
		
		for (Class<?> type : classes) {
			ResidentMetadata cellType = type.getAnnotation(ResidentMetadata.class);
			
			if (IHexResident.class.isAssignableFrom(type)) {
				registerResidentSymbol(cellType.symbol(), type);	
			} else {
				throw new GameException("A class which does not implement IHexResident has been annotated with ResidentMetadata: " + type.getName());
			}
		}		
		
		autoregistered = true;
	}	
	
	/**
	 * Reads an ASCII file map and initializes the game board (including the underlying hex array) based on it.
	 *  
	 * @param game
	 * @param hexMap
	 * @param terrainMapName
	 */
	public static HexArray loadMapTerrain(String terrainMapName) {
		URL mapResource = MapReader.class.getClassLoader().getResource(terrainMapName);
		HexArray hexMap = null;
		
		if (!autoregistered) {
			autoregister();
		}
		
		if (mapResource != null) {
			File mapFile = new File(mapResource.getFile());
	
			if (mapFile.canRead()) {
				Scanner fileScan = null;
				
				try {
					int row = 0;
					
					hexMap = initializeHexMap(mapFile);
					
					fileScan = new Scanner(mapFile);

					while (fileScan.hasNext()) {
						String output = fileScan.nextLine();
						String[] cellTokens = output.trim().split("  ");
	
						for (int j = 0; j < cellTokens.length; j++) {
							int x = (j * 2) + ((row%2) == 1 ? 1 : 0);
							int y = row / 2;
							
							HexCell cell = hexMap.getCellAt(x, y);
							
							if (cell != null) {
								log.debug(cell + " " + cellTokens[j]);
								String typeSymbol = cellTokens[j].substring(0,1);
								String residentSymbol = cellTokens[j].substring(1,2);
								
								makeType(cell, typeSymbol);
								makeResident(cell, residentSymbol);
							} else {
								String msg = "Attempted to configure hex point " + cell + " as " + x + ", " + y + " with '" + cellTokens[j] + "'.  This does not exist on the map, using file coordinates " + row + ", " + j;
								log.error(msg);
								throw new GameException("Could not load map from file " + terrainMapName + ". " + msg);
							}
						}
						row++;
					}
	
				} catch (FileNotFoundException e) {
					throw new GameException("Map file does not exist: " + terrainMapName, e);
				} finally {
					fileScan.close();
				}
	
			} else {
				throw new GameException("Cannot read mapFile: "	+ terrainMapName);
			}
		} else {
			throw new GameException("Cannot find map on the resource path: " + terrainMapName);
		}
		
		return hexMap;
		
	}
	
}
