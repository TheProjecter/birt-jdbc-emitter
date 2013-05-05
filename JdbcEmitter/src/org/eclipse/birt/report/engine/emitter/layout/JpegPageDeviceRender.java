package org.eclipse.birt.report.engine.emitter.layout;

import java.io.OutputStream;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.emitter.EmitterUtil;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.layout.emitter.IPageDevice;
import org.eclipse.birt.report.engine.layout.emitter.PageDeviceRender;

public class JpegPageDeviceRender extends PageDeviceRender {

	private OutputStream output;

	/**
	 * Setup basic emitter services used throughout
	 * @param services
	 */
	public JpegPageDeviceRender(IEmitterServices services) {
		try {
			this.output = EmitterUtil.getOuputStream( services, "report.jpg" );
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called by BIRT to create a page device. 
	 */
	@Override
	public IPageDevice createPageDevice(String title, String author, String subject,
			String comments, IReportContext reportContext, IReportContent report)
			throws Exception {
		this.pageDevice = new JpegLayoutPageDevice(output);
		return this.pageDevice;
	}

	/**
	 * Used to get the output format. This should match what is in the Manifest and Plugin.XML
	 */
	@Override
	public String getOutputFormat() {
		return "jpg";
	}

}
