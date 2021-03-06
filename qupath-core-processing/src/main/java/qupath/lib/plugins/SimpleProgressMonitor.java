/*-
 * #%L
 * This file is part of QuPath.
 * %%
 * Copyright (C) 2014 - 2016 The Queen's University of Belfast, Northern Ireland
 * Contact: IP Management (ipmanagement@qub.ac.uk)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package qupath.lib.plugins;

import qupath.lib.regions.ImageRegion;

/**
 * Class for monitoring the process of a plugin and displaying feedback to the user.
 * 
 * Implementing classes receive notifications from the plugin as it executes, and should display these in an appropriate way -
 * such as with a dialog box and progress bar, or logging the progress to the system output.
 * Classes may also choose to send cancel requests to the plugin, e.g. if the user presses a 'cancel' button.
 * 
 * PluginProgressMonitors are not intended for reuse, i.e. the startMonitoring method should only be called once.
 * 
 * @author Pete Bankhead
 *
 */
public interface SimpleProgressMonitor {
	
	/**
	 * Set the plugin to monitor, and begin monitoring.  Note that since PluginProgressMonitors are not intended for reuse,
	 * this method should only be called once.
	 * 
	 * @param message The message to display
	 * @param maxProgress The progress value considered complete.
	 */
	public void startMonitoring(String message, int maxProgress, boolean mayCancel);
	
	/**
	 * Update the displayed progress, and optionally inform the PluginRunner that data related to a specified image region has been updated.
	 * If progress >= 1 this indicates that the task is finished, and the monitor may stop.
	 * 
	 * @param progress Value between 0 (nothing completed yet) and maxProgress (task finished).
	 * @param message Optional message that may be displayed to reflect the current progress status.
	 * @param region Optional region of the image that has been changed; in interactive mode, this region may be repainted.
	 */
	public void updateProgress(int increment, String message, ImageRegion region);
	
	/**
	 * Notify the monitor that the plugin has completed its work.  This is called automatically by updateProgress if progress >= 1,
	 * but may also be called if the plugin was cancelled or otherwise terminated abnormally.
	 * 
	 * @param pluginCancelled True if the plugin was cancelled rather than completing its operation normally.
	 */
	public void pluginCompleted(String message);
	
	public boolean cancelled();

}