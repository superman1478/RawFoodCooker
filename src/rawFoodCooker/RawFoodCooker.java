package rawFoodCooker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import methods.DebugMethods;
import methods.MyCalculations;
import methods.MyMethods;
import myAPI.SkillTracker;

import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Skills;

import tasks.Bank;
import tasks.Cook;
import tasks.Obsticale;
import tasks.Run;
import tasks.ToBank;
import tasks.ToRange;

@Manifest(name = "Raw Food Cooker", 
description = "Cooks raw Shrimp, sardines, Anchovies, Mackerel, Trout, Salmon, Tuna, Lobster, Swordfish," +
		" Monkfish, Shark, Rocktail, Cavefish, Rat Meat, Beef, Sweetcorn and Potatos.")

public class RawFoodCooker extends PollingScript<ClientContext> implements PaintListener, MessageListener {
	private final double version = 1.1;

	private long startTime = 0;

	private int foodSuccessfullyCooked = 0;

	private RawFood selectedFood;

	private String displayName;

	private String message = null;

	private CookingWatcher cw;

	private boolean debug = true;

	private SkillTracker skillTracker = null;

	private ArrayList<Task> tasks = new ArrayList<Task>();

	private AreaInfo areaInfo = null;

	@Override
	public void start() {

		displayName = ctx.players.local().name();

		RawFoodCookerGUI mygui = new RawFoodCookerGUI(ctx);
		mygui.setVisible(true);

		while (mygui.isVisible()) {
			MyMethods.sleep(1000, 1001);
		}

		mygui.dispose();

		for (AreaInfo current : AreaInfo.values()) {
			if (current.getArea().contains(ctx.players.local())) {
				MyMethods.println("selecting " + current.name());
				areaInfo = current;
			}
		}

		if (areaInfo == null) {
			message = "Unsupported cooking location";
		}

		tasks.add(new Run(ctx));
		tasks.add(new Obsticale(ctx));
		tasks.add(new ToRange(ctx));
		tasks.add(new ToBank(ctx));
		tasks.add(new Bank(ctx, areaInfo));
		tasks.add(new Cook(ctx));


		skillTracker = new SkillTracker(ctx, Skills.COOKING);

		cw = new CookingWatcher(ctx);
		cw.start();

		startTime = System.currentTimeMillis();

		if (message != null) {
			System.out.println(message);
			MyMethods.sleep(5000, 5000);
			ctx.controller.stop();
			return;
		}

		tasks.add(new Run(ctx));

	}

	@Override
	public void poll() {

		for (Task task : tasks) {
			if (task.activate()) {
				DebugMethods.println(debug, task.getClass().getName() + ".execute(");
				task.execute();
			}
		}

	}
	
	public int getFoodSuccessfullyCooked() {
		return foodSuccessfullyCooked;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public boolean getDebug() {
		return debug;
	}
	
	public CookingWatcher getCookingWatcher() {
		return cw;
	}
	
	public AreaInfo getAreaInfo() {
		return areaInfo;
	}
	
	public RawFood getSelectedFood() {
		return selectedFood;
	}
	
	public void setSelectedFood(RawFood selectedFood) {
		this.selectedFood = selectedFood;
	}

	@Override
	public void repaint(Graphics g) {

		if (startTime == 0) {
			return;
		}

		Point m = ctx.input.getLocation();
		g.setColor(Color.cyan);
		g.drawRoundRect(m.x - 6, m.y, 15, 3, 5, 5);
		g.drawRoundRect(m.x, m.y - 6, 3, 15, 5, 5);
		g.fillRoundRect(m.x - 6, m.y, 15, 3, 5, 5);
		g.fillRoundRect(m.x, m.y - 6, 3, 15, 5, 5);

		int xCoord = 5, yCoord = 5;

		if (message != null) {

			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(xCoord - 5, 55, 195, 21);

			g.setColor(Color.RED);
			g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString(message, xCoord, 70);
			return;
		}

		//draw background
		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(xCoord, yCoord, 127, 198);
		xCoord += 5;
		yCoord += 15;


		g.setColor(Color.white);
		g.setFont(new Font("Tahoma", Font.BOLD, 11));
		g.drawString("Raw Food Cooker " + version, xCoord, yCoord);
		yCoord += 15;

		g.setColor(Color.red); 
		g.setFont(new Font("Tahoma", Font.PLAIN, 11));

		g.drawString("Time: " + MyMethods.formattedTimeSince(startTime) , xCoord, yCoord);
		yCoord += 15;

		g.setColor(Color.red);
		g.setFont(new Font("Tahoma", Font.PLAIN, 11));

		double hours = MyCalculations.getHoursSince(startTime);

		double foodCookedPerHour = ((double)foodSuccessfullyCooked) / hours;

		g.drawString("Cooking: " + getSelectedFood().toString(), xCoord, yCoord);
		yCoord += 15;
		g.drawString(getSelectedFood().toString() + " cooked: " + foodSuccessfullyCooked, xCoord, yCoord);
		yCoord += 15;
		g.drawString(getSelectedFood().toString() + " / Hour: " + MyMethods.formatDouble(foodCookedPerHour), xCoord, yCoord);
		yCoord += 15;

		//g.setColor(new Color(0, 138, 184));
		g.setColor(new Color(0, 180, 230));

		g.drawString("Current level: " + skillTracker.getCurrentLevel(), xCoord, yCoord);
		yCoord += 15;
		g.drawString("Levels Gained: " + skillTracker.getLevelsGained(), xCoord, yCoord);
		yCoord += 15;
		g.drawString("Current Exp: " + skillTracker.getCurrentExperience(), xCoord, yCoord);
		yCoord += 15;
		g.drawString("Exp Gained: " + skillTracker.getExperienceGained(), xCoord, yCoord);
		yCoord += 15;
		g.drawString("Exp / Hour: " + MyMethods.formatDouble(skillTracker.getExperiencePerHour()), xCoord, yCoord);
		yCoord += 15;
		g.drawString("Exp TNL: " + skillTracker.getExperienceToNextLevel(), xCoord, yCoord);
		yCoord += 15;
		if (skillTracker.getExperienceGained() != 0) {
			g.drawString("Time TNL: " + MyMethods.formatTime(skillTracker.getMillisecondsToNextLevel()), xCoord, yCoord);
		} else {
			g.drawString("Time TNL: ", xCoord, yCoord);
		}
		yCoord += 8;

		//fill red bar
		g.setColor(Color.RED);
		g.fillRect(xCoord, yCoord, 100, 4);
		g.setColor(Color.GREEN);
		g.fillRect(xCoord, yCoord, (int)Math.round(skillTracker.getPercentOfLevelCompletion()), 4);
		yCoord += 15;

		g.drawString("secondsSinceLastCooking(): " + cw.secondsSinceLastCooking(), xCoord, yCoord);

	}

	@Override
	public void messaged(MessageEvent e) {
		String msg = e.text();
		if(e.source().isEmpty()) {
			if (msg.contains("You succ") 
					|| msg.contains("You roast")
					|| msg.contains("You cook")
					|| msg.contains("You manage to c")) {
				foodSuccessfullyCooked++;
			}
		}
	}	

	@Override
	public void stop() {
		MyMethods.println(displayName + " Stopped after " + MyMethods.formattedTimeSince(startTime));
	}

}
