/*
 * Copyright 2010-2012 Luca Garulli (l.garulli--at--orientechnologies.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orientechnologies.orient.test.database.speed;

import com.orientechnologies.common.test.SpeedTestMultiThreads;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.intent.OIntentMassiveInsert;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.serialization.serializer.record.binary.ORecordSerializerBinary;
import com.orientechnologies.orient.core.tx.OTransaction.TXTYPE;
import com.orientechnologies.orient.test.database.base.OrientMultiThreadTest;
import com.orientechnologies.orient.test.database.base.OrientThreadTest;
import org.testng.annotations.Test;

import java.util.Date;


public class LocalCreateDocumentMultiThreadSpeedTest extends OrientMultiThreadTest {
  private ODatabaseDocumentTx database;
  private long                foundObjects;

  @Test(enabled = false)
  public static class CreateObjectsThread extends OrientThreadTest {
    private ODatabaseDocumentTx database;
    private ODocument           record;
    private Date                date = new Date();

    public CreateObjectsThread(final SpeedTestMultiThreads parent, final int threadId) {
      super(parent, threadId);
    }

    @Override
    public void init() {
      database = new ODatabaseDocumentTx(System.getProperty("url")).open("admin", "admin");
      database.setSerializer(new ORecordSerializerBinary());

      record = database.newInstance();
      database.declareIntent(new OIntentMassiveInsert());
      database.begin(TXTYPE.NOTX);
    }

    public void cycle() {
      record.reset();

      record.setClassName("Account");
      record.field("id", data.getCyclesDone());
      record.field("name", "Luca");
      record.field("surname", "Garulli");
      record.field("birthDate", date);
      record.field("salary", 3000f + data.getCyclesDone());

      record.save();

      if (data.getCyclesDone() == data.getCycles() - 1)
        database.commit();
    }

    @Override
    public void deinit() throws Exception {
      if (database != null)
        database.close();
      super.deinit();
    }
  }

  public LocalCreateDocumentMultiThreadSpeedTest() {
    super(1000000, 8, CreateObjectsThread.class);
  }

  public static void main(String[] iArgs) throws InstantiationException, IllegalAccessException {
    // System.setProperty("url", "memory:test");
    OGlobalConfiguration.USE_WAL.setValue(false);
    OGlobalConfiguration.WAL_SYNC_ON_PAGE_FLUSH.setValue(false);
    LocalCreateDocumentMultiThreadSpeedTest test = new LocalCreateDocumentMultiThreadSpeedTest();
    test.data.go(test);
    OGlobalConfiguration.USE_WAL.setValue(true);
    OGlobalConfiguration.WAL_SYNC_ON_PAGE_FLUSH.setValue(true);
  }

  @Override
  public void init() {
    database = new ODatabaseDocumentTx(System.getProperty("url"));
    database.setSerializer(new ORecordSerializerBinary());
    if (database.exists()) {
      database.open("admin", "admin");
      // else
      database.drop();
    }

    database.create();
    database.set(ODatabase.ATTRIBUTES.MINIMUMCLUSTERS, 8);
    database.getMetadata().getSchema().createClass("Account");

    foundObjects = 0;// database.countClusterElements("Account");

    System.out.println("\nTotal objects in Animal cluster before the test: " + foundObjects);
  }

  @Override
  public void deinit() {
    // long total = database.countClusterElements("Account");
    //
    // System.out.println("\nTotal objects in Account cluster after the test: " + total);
    // System.out.println("Created " + (total - foundObjects));
    // Assert.assertEquals(total - foundObjects, threadCycles);

    int counter = 0;
    for (int i = 0; i < 1000000; i++) {
      for (ODocument doc : database.browseClass("Account"))
        if (doc != null)
          counter++;
    }

    System.out.println(counter);

    if (database != null)
      database.close();
  }
}
