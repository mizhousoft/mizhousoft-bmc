package com.mizhousoft.bmc.auditlog.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.auditlog.domain.LogFile;
import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 本地文件日志抓取控制器
 *
 * @version
 */
@RestController
public class LocalFileLogsFetchController
{
	private static final Logger LOG = LoggerFactory.getLogger(LocalFileLogsFetchController.class);

	@Value("${logs.run.basedir}")
	private String runBaseDir;

	@Value("${logs.download.filters}")
	private String filters;

	@RequestMapping(value = "/runninglog/fetchRunningLogNames.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchRunningLogNames()
	{
		ModelMap map = new ModelMap();

		List<String> lognames = new ArrayList<>(10);

		Map<String, Appender> appenders = LoggerContext.getContext().getConfiguration().getAppenders();
		Iterator<Entry<String, Appender>> iter = appenders.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, Appender> entry = iter.next();
			Appender appender = entry.getValue();
			if (appender instanceof RollingFileAppender)
			{
				String filename = ((RollingFileAppender) appender).getFileName();
				int index = filename.lastIndexOf("/");
				if (index != -1)
				{
					String name = filename.substring(index + 1);
					lognames.add(name);
				}
			}
		}

		lognames = filterLogName(lognames);

		List<LogFile> logFiles = new ArrayList<>(10);
		for (String logname : lognames)
		{
			List<LogFile> files = getLogFileNames(logname);
			logFiles.addAll(files);
		}
		map.put("logFiles", logFiles);

		return map;
	}

	@RequestMapping(value = "/runninglog/downloadRunningLogFile.action", method = RequestMethod.GET)
	public String downloadRunningLogFile(@RequestParam("logname") String logname, HttpServletResponse response)
	{
		try
		{
			String filename = new String(logname.getBytes(CharEncoding.UTF8), "iso-8859-1");
			response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error("Charset not supported.", e);
		}

		File file = new File(runBaseDir, logname);

		byte[] buffer = new byte[1024];

		try (InputStream io = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(io);)
		{
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1)
			{
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}

			return null;
		}
		catch (Exception e)
		{
			LOG.error("Download log file failed.", e);
		}

		return null;
	}

	private List<String> filterLogName(List<String> lognames)
	{
		if (StringUtils.isBlank(filters))
		{
			return lognames;
		}

		String[] fs = filters.split(",");

		Iterator<String> iter = lognames.iterator();
		while (iter.hasNext())
		{
			String name = iter.next();
			if (ArrayUtils.contains(fs, name))
			{
				iter.remove();
			}
		}

		return lognames;
	}

	private List<LogFile> getLogFileNames(String logname)
	{
		logname = StringUtils.removeEnd(logname, ".log");
		List<LogFile> logFilenames = new ArrayList<>(10);

		File dir = new File(runBaseDir);
		String[] filenames = dir.list();
		if (null != filenames)
		{
			for (String name : filenames)
			{
				if (name.contains(logname))
				{
					LogFile logFile = new LogFile();
					logFile.setName(name);

					File file = new File(dir, name);
					String size = FileUtils.byteCountToDisplaySize(file.length());
					logFile.setSize(size);
					logFile.setLastModified(LocalDateTimeUtils.toLocalDateTime(file.lastModified() / 1000));

					logFilenames.add(logFile);
				}
			}
		}

		Collections.sort(logFilenames);

		return logFilenames;
	}
}
