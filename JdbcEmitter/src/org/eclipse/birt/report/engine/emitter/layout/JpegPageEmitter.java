package org.eclipse.birt.report.engine.emitter.layout;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.layout.emitter.PageDeviceRender;
import org.eclipse.birt.report.engine.layout.emitter.PageEmitter;

public class JpegPageEmitter extends PageEmitter {

	@Override
	public PageDeviceRender createRender(IEmitterServices services)
			throws EngineException {
		return new JpegPageDeviceRender( services );
	}

	
}
