/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2019, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.tools.options;


import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Optional;

import schemacrawler.schemacrawler.Options;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.tools.iosource.FileOutputResource;
import schemacrawler.tools.iosource.OutputResource;
import sf.util.ObjectToString;

/**
 * Contains output options.
 *
 * @author Sualeh Fatehi
 */
public final class OutputOptions
  implements Options
{

  private final OutputResource outputResource;
  private final String outputFormatValue;
  private final Charset inputEncodingCharset;
  private final Charset outputEncodingCharset;
  private final String title;

  OutputOptions(final Charset inputEncodingCharset,
                final OutputResource outputResource,
                final Charset outputEncodingCharset,
                final String outputFormatValue,
                final String title)
  {
    this.inputEncodingCharset = requireNonNull(inputEncodingCharset,
                                               "No input encoding provided");
    this.outputResource = requireNonNull(outputResource,
                                         "No output resource provided");
    this.outputEncodingCharset = requireNonNull(outputEncodingCharset,
                                                "No output encoding provided");
    this.outputFormatValue = requireNonNull(outputFormatValue,
                                            "No output format value provided");
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }

  /**
   * Character encoding for input files, such as scripts and templates.
   */
  public Charset getInputCharset()
  {
    return inputEncodingCharset;
  }

  /**
   * Character encoding for output files.
   */
  public Charset getOutputCharset()
  {
    return outputEncodingCharset;
  }

  public Optional<Path> getOutputFile()
  {
    final Path outputFile;
    if (outputResource instanceof FileOutputResource)
    {
      outputFile = ((FileOutputResource) outputResource).getOutputFile();
    }
    else
    {
      outputFile = null;
    }
    return Optional.ofNullable(outputFile);
  }

  /**
   * Gets the output format value.
   *
   * @return Output format value.
   */
  public String getOutputFormatValue()
  {
    return outputFormatValue;
  }

  /**
   * Gets the output reader. If the output resource is null, first set
   * it to console output.
   *
   * @return Output writer
   * @throws IOException On an exception
   */
  public Writer openNewOutputWriter()
    throws IOException
  {
    return openNewOutputWriter(false);
  }

  /**
   * Gets the output reader. If the output resource is null, first set
   * it to console output.
   *
   * @throws SchemaCrawlerException
   */
  public Writer openNewOutputWriter(final boolean appendOutput)
    throws IOException
  {
    return outputResource.openNewOutputWriter(getOutputCharset(), appendOutput);
  }

  @Override
  public String toString()
  {
    return ObjectToString.toString(this);
  }

  OutputResource getOutputResource()
  {
    return outputResource;
  }

}
