package se.fidde.murder.controllers;

import se.fidde.murder.view.scenes.ScreenProvider;

/**
 * Interface for screen transition API
 * 
 * @author fidde
 * 
 */
public interface ScreenController {

	public void setParentController(ScreenProvider controller);
}
