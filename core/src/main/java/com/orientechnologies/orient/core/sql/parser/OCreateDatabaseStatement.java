/* Generated By:JJTree: Do not edit this line. OCreateDatabaseStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.command.OServerCommandContext;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.*;
import com.orientechnologies.orient.core.exception.OCommandExecutionException;
import com.orientechnologies.orient.core.sql.executor.OInternalResultSet;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OCreateDatabaseStatement extends OSimpleExecServerStatement {

  protected OIdentifier name;
  protected OInputParameter nameParam;
  protected OIdentifier type;
  protected boolean ifNotExists = false;
  protected OJson config;

  List<ODatabaseUserData> users = new ArrayList<>();

  public OCreateDatabaseStatement(int id) {
    super(id);
  }

  public OCreateDatabaseStatement(OrientSql p, int id) {
    super(p, id);
  }

  @Override
  public OResultSet executeSimple(OServerCommandContext ctx) {
    OrientDBInternal server = ctx.getServer();
    OResultInternal result = new OResultInternal();
    result.setProperty("operation", "create database");
    String dbName =
        name != null
            ? name.getStringValue()
            : String.valueOf(nameParam.getValue(ctx.getInputParameters()));
    result.setProperty("name", dbName);

    ODatabaseType dbType;
    try {

      dbType = ODatabaseType.valueOf(type.getStringValue().toUpperCase(Locale.ENGLISH));
    } catch (IllegalArgumentException ex) {
      throw new OCommandExecutionException("Invalid db type: " + type.getStringValue());
    }
    if (ifNotExists && server.exists(dbName, null, null)) {
      result.setProperty("created", false);
      result.setProperty("existing", true);
    } else {
      try {
        OrientDBConfigBuilder configBuilder = OrientDBConfig.builder();

        if (config != null) {
          configBuilder = mapOrientDBConfig(this.config, ctx, configBuilder);
        }

        if (!users.isEmpty()) {
          configBuilder = configBuilder.addConfig(OGlobalConfiguration.CREATE_DEFAULT_USERS, false);
        }

        server.create(
            dbName,
            null,
            null,
            dbType,
            configBuilder.build(),
            (session) -> {
              if (!users.isEmpty()) {
                for (ODatabaseUserData user : users) {
                  user.executeCreate((ODatabaseDocumentInternal) session, ctx);
                }
              }
              return null;
            });
        result.setProperty("created", true);
      } catch (Exception e) {
        throw OException.wrapException(
            new OCommandExecutionException(
                "Could not create database " + type.getStringValue() + ":" + e.getMessage()),
            e);
      }
    }

    OInternalResultSet rs = new OInternalResultSet();
    rs.add(result);
    return rs;
  }

  private OrientDBConfigBuilder mapOrientDBConfig(
      OJson config, OServerCommandContext ctx, OrientDBConfigBuilder builder) {
    Map<String, Object> configMap = config.toMap(new OResultInternal(), ctx);
    Object globalConfig = configMap.get("config");
    if (globalConfig != null && globalConfig instanceof Map) {
      ((Map<String, Object>) globalConfig)
          .entrySet().stream()
              .filter(x -> OGlobalConfiguration.findByKey(x.getKey()) != null)
              .forEach(
                  x -> builder.addConfig(OGlobalConfiguration.findByKey(x.getKey()), x.getValue()));
    }
    return builder;
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("CREATE DATABASE ");
    if (name != null) {
      name.toString(params, builder);
    } else {
      nameParam.toString(params, builder);
    }
    builder.append(" ");
    type.toString(params, builder);
    if (ifNotExists) {
      builder.append(" IF NOT EXISTS");
    }

    if (!users.isEmpty()) {
      builder.append(" USERS (");
      boolean first = true;
      for (ODatabaseUserData user : users) {
        if (!first) {
          builder.append(", ");
        }
        user.toString(params, builder);
        first = false;
      }
      builder.append(")");
    }
    if (config != null) {
      builder.append(" ");
      config.toString(params, builder);
    }
  }
}
/* JavaCC - OriginalChecksum=99888a0f8bb929dce0904816cd51fefe (do not edit this line) */
