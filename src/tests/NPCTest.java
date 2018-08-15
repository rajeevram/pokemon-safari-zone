package tests;

import org.junit.Rule;
import org.junit.Test;

import model.npc.Brother;
import model.npc.Gentleman;
import model.npc.Merchant;
import model.npc.NPC;
import model.npc.OldLady;
import model.npc.ProfessorOak;
import model.npc.RegularMan;
import model.npc.SafariAgent;
import model.npc.Sister;
import model.npc.Woman;

public class NPCTest {

	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Test
	public void testsafAgentInstance() {

		NPC npc = new OldLady();
		npc.equals(npc);
		npc.getMessageOne();
		npc.getMessageTwo();
		npc.getName();
		npc.getImage();
		NPC gentleMan = new Gentleman();
		gentleMan.equals(gentleMan);
		gentleMan.getMessageOne();
		gentleMan.getMessageTwo();
		gentleMan.getName();
		gentleMan.getImage();
		NPC regularMar = new RegularMan();
		regularMar.equals(regularMar);
		regularMar.getMessageOne();
		regularMar.getMessageTwo();
		regularMar.getName();
		regularMar.getImage();
		NPC woman = new Woman();
		woman.equals(woman);
		woman.getMessageOne();
		woman.getMessageTwo();
		woman.getName();
		woman.getImage();

		NPC brother = new Brother();
		brother.equals(brother);
		brother.getMessageOne();
		brother.getMessageTwo();
		brother.getName();
		brother.getImage();
		NPC sis = new Sister();
		sis.equals(sis);
		sis.getMessageOne();
		sis.getMessageTwo();
		sis.getName();
		sis.getImage();
		NPC merch = new Merchant("Guy");
		merch.equals(merch);
		merch.getMessageOne();
		merch.getMessageTwo();
		merch.getName();
		merch.getImage();
		((Merchant)merch).getInventory();
		((Merchant)merch).setImages();
		NPC prof = new ProfessorOak();
		prof.equals(prof);
		prof.getMessageOne();
		prof.getMessageTwo();
		prof.getName();
		prof.getImage();
		NPC safAgent = new SafariAgent();
		safAgent.equals(safAgent);
		safAgent.getMessageOne();
		safAgent.getMessageTwo();
		safAgent.getName();
		safAgent.getImage();

	}
}
