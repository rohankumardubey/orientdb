/* Generated By:JJTree: Do not edit this line. OTruncateClassStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.exception.OCommandExecutionException;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.resultset.OExecutionStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OTruncateClassStatement extends ODDLStatement {

  protected OIdentifier className;
  protected boolean polymorphic = false;
  protected boolean unsafe = false;

  public OTruncateClassStatement(int id) {
    super(id);
  }

  public OTruncateClassStatement(OrientSql p, int id) {
    super(p, id);
  }

  @Override
  public OExecutionStream executeDDL(OCommandContext ctx) {
    ODatabaseDocumentInternal db = (ODatabaseDocumentInternal) ctx.getDatabase();
    OSchema schema = db.getMetadata().getSchema();
    OClass clazz = schema.getClass(className.getStringValue());
    if (clazz == null) {
      throw new OCommandExecutionException("Schema Class not found: " + className);
    }

    final long recs = clazz.count(polymorphic);
    if (recs > 0 && !unsafe) {
      if (clazz.isSubClassOf("V")) {
        throw new OCommandExecutionException(
            "'TRUNCATE CLASS' command cannot be used on not empty vertex classes. Apply the 'UNSAFE' keyword to force it (at your own risk)");
      } else if (clazz.isSubClassOf("E")) {
        throw new OCommandExecutionException(
            "'TRUNCATE CLASS' command cannot be used on not empty edge classes. Apply the 'UNSAFE' keyword to force it (at your own risk)");
      }
    }

    List<OResult> rs = new ArrayList();
    Collection<OClass> subclasses = clazz.getAllSubclasses();
    if (polymorphic && !unsafe) { // for multiple inheritance
      for (OClass subclass : subclasses) {
        long subclassRecs = clazz.count();
        if (subclassRecs > 0) {
          if (subclass.isSubClassOf("V")) {
            throw new OCommandExecutionException(
                "'TRUNCATE CLASS' command cannot be used on not empty vertex classes ("
                    + subclass.getName()
                    + "). Apply the 'UNSAFE' keyword to force it (at your own risk)");
          } else if (subclass.isSubClassOf("E")) {
            throw new OCommandExecutionException(
                "'TRUNCATE CLASS' command cannot be used on not empty edge classes ("
                    + subclass.getName()
                    + "). Apply the 'UNSAFE' keyword to force it (at your own risk)");
          }
        }
      }
    }

    long count = db.truncateClass(clazz.getName(), false);
    OResultInternal result = new OResultInternal();
    result.setProperty("operation", "truncate class");
    result.setProperty("className", className.getStringValue());
    result.setProperty("count", count);
    rs.add(result);
    if (polymorphic) {
      for (OClass subclass : subclasses) {
        count = db.truncateClass(subclass.getName(), false);
        result = new OResultInternal();
        result.setProperty("operation", "truncate class");
        result.setProperty("className", className.getStringValue());
        result.setProperty("count", count);
        rs.add(result);
      }
    }

    return OExecutionStream.resultIterator(rs.iterator());
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("TRUNCATE CLASS ");
    className.toString(params, builder);
    if (polymorphic) {
      builder.append(" POLYMORPHIC");
    }
    if (unsafe) {
      builder.append(" UNSAFE");
    }
  }

  @Override
  public void toGenericStatement(StringBuilder builder) {
    builder.append("TRUNCATE CLASS ");
    className.toGenericStatement(builder);
    if (polymorphic) {
      builder.append(" POLYMORPHIC");
    }
    if (unsafe) {
      builder.append(" UNSAFE");
    }
  }

  @Override
  public OTruncateClassStatement copy() {
    OTruncateClassStatement result = new OTruncateClassStatement(-1);
    result.className = className == null ? null : className.copy();
    result.polymorphic = polymorphic;
    result.unsafe = unsafe;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OTruncateClassStatement that = (OTruncateClassStatement) o;

    if (polymorphic != that.polymorphic) return false;
    if (unsafe != that.unsafe) return false;
    if (className != null ? !className.equals(that.className) : that.className != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = className != null ? className.hashCode() : 0;
    result = 31 * result + (polymorphic ? 1 : 0);
    result = 31 * result + (unsafe ? 1 : 0);
    return result;
  }
}
/* JavaCC - OriginalChecksum=301f993f6ba2893cb30c8f189674b974 (do not edit this line) */
