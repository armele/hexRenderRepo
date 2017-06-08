package com.mele.games.utils.hexarray;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	protected HashMap<String, Class<?>> symbolType = new HashMap<String, Class<?>>();
	protected HashMap<String, Class<?>> symbolResident = new HashMap<String, Class<?>>();
	
	public MapReader () {

	}
	
	
	/**
	 * @param mapFile
	 * @return
	 * @throws FileNotFoundException
	 */
	protected HexArray initializeHexMap(File mapFile) throws FileNotFoundException {
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
	public void registerTypeSymbol(String symbol, Class<?> type) {
		symbolType.put(symbol, type);
	}
	
	/**
	 * @param symbol
	 * @param resident
	 */
	public void registerResidentSymbol(String symbol, Class<?> resident) {
		symbolResident.put(symbol, resident);
	}	
	
	/**
	 * @param cell
	 * @param residentSymbol
	 */
	protected void makeResident(HexCell cell, String residentSymbol) {
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
	protected void makeType(HexCell cell, String typeSymbol) {
		try {
			Class<?> resClass = symbolType.get(typeSymbol);
			if (resClass != null) {
				if (ICellType.class.isAssignableFrom(resClass)) {
					ICellType type = (ICellType)resClass.newInstance();
					cell.setType(type);
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
	 * Reads an ASCII file map and initializes the game board (including the underlying hex array) based on it.
	 *  
	 * @param game
	 * @param hexMap
	 * @param terrainMapName
	 */
	public HexArray loadMapTerrain(String terrainMapName) {
		URL mapResource = this.getClass().getClassLoader().getResource(terrainMapName);
		HexArray hexMap = null;
		
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
