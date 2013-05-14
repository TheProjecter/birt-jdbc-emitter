package org.eclipse.birt.report.engine.emitter.jdbcemitter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IAutoTextContent;
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.IContainerContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.content.IForeignContent;
import org.eclipse.birt.report.engine.content.IGroupContent;
import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.content.ILabelContent;
import org.eclipse.birt.report.engine.content.IListBandContent;
import org.eclipse.birt.report.engine.content.IListContent;
import org.eclipse.birt.report.engine.content.IListGroupContent;
import org.eclipse.birt.report.engine.content.IPageContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.content.IRowContent;
import org.eclipse.birt.report.engine.content.ITableBandContent;
import org.eclipse.birt.report.engine.content.ITableContent;
import org.eclipse.birt.report.engine.content.ITableGroupContent;
import org.eclipse.birt.report.engine.content.ITextContent;
import org.eclipse.birt.report.engine.emitter.IContentEmitter;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.emitter.jdbcsession.JdbcSession;
import org.eclipse.birt.report.engine.emitter.tablebuild.TableBuilder;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.elements.Cell;
import org.eclipse.birt.report.model.elements.DataItem;
import org.eclipse.birt.report.model.elements.interfaces.IDataItemModel;

public class JdbcEmitter implements IContentEmitter 
{

  protected static Logger logger = Logger.getLogger( JdbcEmitter.class.getName( ) );

  protected IEmitterServices service = null;

  protected JdbcSession session = null;
  protected IReportContent report;
  protected ITableContent table = null;
  private String propertiesFile = null;
  private TableBuilder builder = null;
  private String driverPath =null;

    //ArrayList of columns used in Table in the same order as they appear in Report Design
	List<String> columnNamesInTableOrder = new ArrayList<String> ();
    ArrayList<Object> data = new ArrayList<Object>();

    public void initialize(IEmitterServices service) throws BirtException 
	{
	 	this.service = service;
	 	this.driverPath = (String) service.getReportContext().getAppContext().get("OdaJDBCDriverClassPath");
	}

	public void start(IReportContent report) throws BirtException 
	{
		this.report = report;
		
		ReportDesignHandle reportDesignHandle=report.getDesign().getReportDesign();
		propertiesFile = reportDesignHandle.getResourceFolder()+File.separatorChar+"DataBaseDetails.properties";
		
		File file = null;
		if ( propertiesFile != null && propertiesFile.trim().length() != 0 )
		{
			file = new File(propertiesFile);
			if (file != null && file.exists())
			{
				session = new JdbcSession(file);
				session.start(this.driverPath);
				this.builder = new TableBuilder(session);
				return;
			}
		}
		throw new BirtException("Property file could not find");
	}
	
	public void startTable(ITableContent table) throws BirtException 
	{
		if(	this.table != null && this.table != table)
		{
			this.builder.flush();
			columnNamesInTableOrder.clear();
		}
		this.table = table;
		
		if(this.table.getName() == null || this.table.getName().trim().length() == 0)
		{
			this.table.setName("Test_Table");
		}
	}
	
	public void startRow(IRowContent row) throws BirtException 
	{
		if( row.getRowID()==1 )
		{
		 deriveColumnNames(row);
		 this.builder.buildTableInfo(table.getName(), columnNamesInTableOrder);
		}
	}

	public void startData(IDataContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub
		data.add(arg0.getText());
	}

	public void endRow(IRowContent row) throws BirtException 
	{
		if( row.getRowID()>=1 )
		{
		this.builder.addRecord(data);
		data.clear();
		}
	}

	public void endTable(ITableContent arg0) throws BirtException 
	{
	}

	private void deriveColumnNames(IRowContent row)
	{		
		ReportDesignHandle reportDesignHandle=report.getDesign().getReportDesign();
					
		Object obj=reportDesignHandle.getElementByID(row.getInstanceID().getComponentID());
					
		RowHandle rowHandle=null;
					
		if (obj instanceof RowHandle) 
		{
			rowHandle = (RowHandle) obj;
		} 
		else
		{
			return; // not a row handle, nothing to do
		}

		@SuppressWarnings("unchecked")
		ArrayList<CellHandle> cells = (ArrayList<CellHandle>) rowHandle.getCells().getContents();

		for (CellHandle cellHandle : cells) 
		{
			Cell cell = (Cell) cellHandle.getElement();

			@SuppressWarnings("rawtypes")
			ArrayList cellContents = (ArrayList) cell.getSlot(0).getContents();

			// Currently hard coded to get the first content only
			if (cellContents.get(0) instanceof DataItem) 
			{
				DataItem cellDataItem = (DataItem) cellContents.get(0);
				columnNamesInTableOrder.add((String) cellDataItem.getLocalProperty(report.getDesign().getReportDesign().getModule(),IDataItemModel.RESULT_SET_COLUMN_PROP));
			}
		}
    }		
	
	public void end(IReportContent arg0) throws BirtException
	{
		this.builder.flush();
		session.stop();
		columnNamesInTableOrder.clear();
	}


	public void endCell(ICellContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void endContainer(IContainerContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub

	}


	public void endContent(IContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void endGroup(IGroupContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void endList(IListContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void endListBand(IListBandContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}

	public void endListGroup(IListGroupContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void endPage(IPageContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}
	
	public void endTableBand(ITableBandContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void endTableGroup(ITableGroupContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub

	}


	public String getOutputFormat()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public void startAutoText(IAutoTextContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub

	}


	public void startCell(ICellContent c) throws BirtException
	{
	//	System.out.println(c.getColumn());
	}


	public void startContainer(IContainerContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void startContent(IContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub

	}
	public void startForeign(IForeignContent arg0) throws BirtException {
		// TODO Auto-generated method stub

	}


	public void startGroup(IGroupContent arg0) throws BirtException {
		// TODO Auto-generated method stub

	}


	public void startImage(IImageContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void startLabel(ILabelContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void startList(IListContent arg0) throws BirtException {
		// TODO Auto-generated method stub

	}


	public void startListBand(IListBandContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub

	}


	public void startListGroup(IListGroupContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}


	public void startPage(IPageContent arg0) throws BirtException 
	{
		// TODO Auto-generated method stub

	}
	public void startTableBand(ITableBandContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub
	}


	public void startTableGroup(ITableGroupContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub
	}

	public void startText(ITextContent arg0) throws BirtException
	{
		// TODO Auto-generated method stub
	}



}
	
	

