/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.util.*;

// line 53 "../../../../../Block223Persistence.ump"
// line 111 "../../../../../Block223.ump"
public class Block implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int MIN_COLOR = 0;
  public static final int MAX_COLOR = 255;
  public static final int MIN_POINTS = 1;
  public static final int MAX_POINTS = 1000;
  public static final int SIZE = 20;
  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Block Attributes
  private int red;
  private int green;
  private int blue;
  private int points;

  //Autounique Attributes
  private int id;

  //Block Associations
  private Game game;
  private List<BlockAssignment> blockAssignments;
  private List<BlockOccurence> blockOccurences;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Block(int aRed, int aGreen, int aBlue, int aPoints, Game aGame)
  {
    // line 125 "../../../../../Block223.ump"
    if (aRed < MIN_COLOR || aRed > MAX_COLOR) {
       	throw new RuntimeException("Red must be between " +MIN_COLOR+ " and "+MAX_COLOR+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    // line 133 "../../../../../Block223.ump"
    if (aGreen < MIN_COLOR || aGreen > MAX_COLOR) {
       	throw new RuntimeException("Green must be between " +MIN_COLOR+ " and "+MAX_COLOR+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    // line 141 "../../../../../Block223.ump"
    if (aBlue < MIN_COLOR || aBlue > MAX_COLOR) {
       		throw new RuntimeException("Blue must be between " +MIN_COLOR+ " and "+MAX_COLOR+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    // line 149 "../../../../../Block223.ump"
    if (aPoints < MIN_POINTS || aPoints > MAX_POINTS) {
       		throw new RuntimeException("Points must be between "+MIN_POINTS+" and "+MAX_POINTS+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    red = aRed;
    green = aGreen;
    blue = aBlue;
    points = aPoints;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create block due to game");
    }
    blockAssignments = new ArrayList<BlockAssignment>();
    blockOccurences = new ArrayList<BlockOccurence>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRed(int aRed)
  {
    boolean wasSet = false;
    // line 125 "../../../../../Block223.ump"
    if (aRed < MIN_COLOR || aRed > MAX_COLOR) {
       	throw new RuntimeException("Red must be between " +MIN_COLOR+ " and "+MAX_COLOR+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    red = aRed;
    wasSet = true;
    return wasSet;
  }

  public boolean setGreen(int aGreen)
  {
    boolean wasSet = false;
    // line 133 "../../../../../Block223.ump"
    if (aGreen < MIN_COLOR || aGreen > MAX_COLOR) {
       	throw new RuntimeException("Green must be between " +MIN_COLOR+ " and "+MAX_COLOR+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    green = aGreen;
    wasSet = true;
    return wasSet;
  }

  public boolean setBlue(int aBlue)
  {
    boolean wasSet = false;
    // line 141 "../../../../../Block223.ump"
    if (aBlue < MIN_COLOR || aBlue > MAX_COLOR) {
       		throw new RuntimeException("Blue must be between " +MIN_COLOR+ " and "+MAX_COLOR+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    blue = aBlue;
    wasSet = true;
    return wasSet;
  }

  public boolean setPoints(int aPoints)
  {
    boolean wasSet = false;
    // line 149 "../../../../../Block223.ump"
    if (aPoints < MIN_POINTS || aPoints > MAX_POINTS) {
       		throw new RuntimeException("Points must be between "+MIN_POINTS+" and "+MAX_POINTS+".");
       	}
    // END OF UMPLE BEFORE INJECTION
    points = aPoints;
    wasSet = true;
    return wasSet;
  }

  public int getRed()
  {
    return red;
  }

  public int getGreen()
  {
    return green;
  }

  public int getBlue()
  {
    return blue;
  }

  public int getPoints()
  {
    return points;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public BlockAssignment getBlockAssignment(int index)
  {
    BlockAssignment aBlockAssignment = blockAssignments.get(index);
    return aBlockAssignment;
  }

  public List<BlockAssignment> getBlockAssignments()
  {
    List<BlockAssignment> newBlockAssignments = Collections.unmodifiableList(blockAssignments);
    return newBlockAssignments;
  }

  public int numberOfBlockAssignments()
  {
    int number = blockAssignments.size();
    return number;
  }

  public boolean hasBlockAssignments()
  {
    boolean has = blockAssignments.size() > 0;
    return has;
  }

  public int indexOfBlockAssignment(BlockAssignment aBlockAssignment)
  {
    int index = blockAssignments.indexOf(aBlockAssignment);
    return index;
  }
  /* Code from template association_GetMany */
  public BlockOccurence getBlockOccurence(int index)
  {
    BlockOccurence aBlockOccurence = blockOccurences.get(index);
    return aBlockOccurence;
  }

  public List<BlockOccurence> getBlockOccurences()
  {
    List<BlockOccurence> newBlockOccurences = Collections.unmodifiableList(blockOccurences);
    return newBlockOccurences;
  }

  public int numberOfBlockOccurences()
  {
    int number = blockOccurences.size();
    return number;
  }

  public boolean hasBlockOccurences()
  {
    boolean has = blockOccurences.size() > 0;
    return has;
  }

  public int indexOfBlockOccurence(BlockOccurence aBlockOccurence)
  {
    int index = blockOccurences.indexOf(aBlockOccurence);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeBlock(this);
    }
    game.addBlock(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockAssignment addBlockAssignment(int aGridHorizontalPosition, int aGridVerticalPosition, Level aLevel, Game aGame)
  {
    return new BlockAssignment(aGridHorizontalPosition, aGridVerticalPosition, aLevel, this, aGame);
  }

  public boolean addBlockAssignment(BlockAssignment aBlockAssignment)
  {
    boolean wasAdded = false;
    if (blockAssignments.contains(aBlockAssignment)) { return false; }
    Block existingBlock = aBlockAssignment.getBlock();
    boolean isNewBlock = existingBlock != null && !this.equals(existingBlock);
    if (isNewBlock)
    {
      aBlockAssignment.setBlock(this);
    }
    else
    {
      blockAssignments.add(aBlockAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlockAssignment(BlockAssignment aBlockAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlockAssignment, as it must always have a block
    if (!this.equals(aBlockAssignment.getBlock()))
    {
      blockAssignments.remove(aBlockAssignment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAssignmentAt(BlockAssignment aBlockAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addBlockAssignment(aBlockAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockAssignments()) { index = numberOfBlockAssignments() - 1; }
      blockAssignments.remove(aBlockAssignment);
      blockAssignments.add(index, aBlockAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAssignmentAt(BlockAssignment aBlockAssignment, int index)
  {
    boolean wasAdded = false;
    if(blockAssignments.contains(aBlockAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockAssignments()) { index = numberOfBlockAssignments() - 1; }
      blockAssignments.remove(aBlockAssignment);
      blockAssignments.add(index, aBlockAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAssignmentAt(aBlockAssignment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockOccurences()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockOccurence addBlockOccurence(int aGridHorizontalPosition, int aGridVerticalPosition, GameOccurence aGameOccurence)
  {
    return new BlockOccurence(aGridHorizontalPosition, aGridVerticalPosition, this, aGameOccurence);
  }

  public boolean addBlockOccurence(BlockOccurence aBlockOccurence)
  {
    boolean wasAdded = false;
    if (blockOccurences.contains(aBlockOccurence)) { return false; }
    Block existingBlock = aBlockOccurence.getBlock();
    boolean isNewBlock = existingBlock != null && !this.equals(existingBlock);
    if (isNewBlock)
    {
      aBlockOccurence.setBlock(this);
    }
    else
    {
      blockOccurences.add(aBlockOccurence);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlockOccurence(BlockOccurence aBlockOccurence)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlockOccurence, as it must always have a block
    if (!this.equals(aBlockOccurence.getBlock()))
    {
      blockOccurences.remove(aBlockOccurence);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockOccurenceAt(BlockOccurence aBlockOccurence, int index)
  {  
    boolean wasAdded = false;
    if(addBlockOccurence(aBlockOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockOccurences()) { index = numberOfBlockOccurences() - 1; }
      blockOccurences.remove(aBlockOccurence);
      blockOccurences.add(index, aBlockOccurence);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockOccurenceAt(BlockOccurence aBlockOccurence, int index)
  {
    boolean wasAdded = false;
    if(blockOccurences.contains(aBlockOccurence))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockOccurences()) { index = numberOfBlockOccurences() - 1; }
      blockOccurences.remove(aBlockOccurence);
      blockOccurences.add(index, aBlockOccurence);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockOccurenceAt(aBlockOccurence, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeBlock(this);
    }
    for(int i=blockAssignments.size(); i > 0; i--)
    {
      BlockAssignment aBlockAssignment = blockAssignments.get(i - 1);
      aBlockAssignment.delete();
    }
    for(int i=blockOccurences.size(); i > 0; i--)
    {
      BlockOccurence aBlockOccurence = blockOccurences.get(i - 1);
      aBlockOccurence.delete();
    }
  }

  // line 59 "../../../../../Block223Persistence.ump"
   public static  void reinitializeAutouniqueBlockID(List<Game> gamesList){
    nextId = 0;
		for (Game game : gamesList) {
			List<Block> blocks = game.getBlocks();
		
			for (Block block : blocks) {
			if (block.getId() > nextId) {
				nextId = block.getId();
			}
		}
		nextId++;
		}
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "red" + ":" + getRed()+ "," +
            "green" + ":" + getGreen()+ "," +
            "blue" + ":" + getBlue()+ "," +
            "points" + ":" + getPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 56 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 5332292624658907512L ;

  
}