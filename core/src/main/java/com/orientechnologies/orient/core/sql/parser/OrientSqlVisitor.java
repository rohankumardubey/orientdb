/* Generated By:JavaCC: Do not edit this line. OrientSqlVisitor.java Version 5.0 */
package com.orientechnologies.orient.core.sql.parser;

public interface OrientSqlVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ORid node, Object data);
  public Object visit(Oparse node, Object data);
  public Object visit(OIdentifier node, Object data);
  public Object visit(OInteger node, Object data);
  public Object visit(OFloatingPoint node, Object data);
  public Object visit(ONumber node, Object data);
  public Object visit(OStatement node, Object data);
  public Object visit(OSelectWithoutTargetStatement node, Object data);
  public Object visit(OSelectStatement node, Object data);
  public Object visit(OTraverseStatement node, Object data);
  public Object visit(ODeleteStatement node, Object data);
  public Object visit(ODeleteVertexStatement node, Object data);
  public Object visit(ODeleteEdgeStatement node, Object data);
  public Object visit(ODeleteEdgeByRidStatement node, Object data);
  public Object visit(ODeleteEdgeFromToStatement node, Object data);
  public Object visit(ODeleteEdgeToStatement node, Object data);
  public Object visit(ODeleteEdgeVToStatement node, Object data);
  public Object visit(ODeleteEdgeWhereStatement node, Object data);
  public Object visit(OUpdateStatement node, Object data);
  public Object visit(OUpdateOperations node, Object data);
  public Object visit(OUpdateItem node, Object data);
  public Object visit(OUpdateIncrementItem node, Object data);
  public Object visit(OUpdateRemoveItem node, Object data);
  public Object visit(OUpdatePutItem node, Object data);
  public Object visit(OUpdateAddItem node, Object data);
  public Object visit(OInsertStatement node, Object data);
  public Object visit(OInsertBody node, Object data);
  public Object visit(OCreateVertexStatementEmptyNoTarget node, Object data);
  public Object visit(OCreateVertexStatement node, Object data);
  public Object visit(OCreateVertexStatementNoTarget node, Object data);
  public Object visit(OCreateEdgeStatement node, Object data);
  public Object visit(OInputParameter node, Object data);
  public Object visit(OPositionalParameter node, Object data);
  public Object visit(ONamedParameter node, Object data);
  public Object visit(OProjection node, Object data);
  public Object visit(OProjectionItem node, Object data);
  public Object visit(OArraySelector node, Object data);
  public Object visit(OArrayNumberSelector node, Object data);
  public Object visit(OArraySingleValuesSelector node, Object data);
  public Object visit(OArrayRangeSelector node, Object data);
  public Object visit(OAlias node, Object data);
  public Object visit(ORecordAttribute node, Object data);
  public Object visit(OFunctionCall node, Object data);
  public Object visit(OMethodCall node, Object data);
  public Object visit(OLevelZeroIdentifier node, Object data);
  public Object visit(OSuffixIdentifier node, Object data);
  public Object visit(OBaseIdentifier node, Object data);
  public Object visit(OModifier node, Object data);
  public Object visit(OExpression node, Object data);
  public Object visit(OMathExpression node, Object data);
  public Object visit(OFirstLevelExpression node, Object data);
  public Object visit(OParenthesisExpression node, Object data);
  public Object visit(OBaseExpression node, Object data);
  public Object visit(OFromClause node, Object data);
  public Object visit(OLetClause node, Object data);
  public Object visit(OLetItem node, Object data);
  public Object visit(OFromItem node, Object data);
  public Object visit(OCluster node, Object data);
  public Object visit(OMetadataIdentifier node, Object data);
  public Object visit(OIndexIdentifier node, Object data);
  public Object visit(OWhereClause node, Object data);
  public Object visit(OOrBlock node, Object data);
  public Object visit(OAndBlock node, Object data);
  public Object visit(ONotBlock node, Object data);
  public Object visit(OParenthesisBlock node, Object data);
  public Object visit(OConditionBlock node, Object data);
  public Object visit(OCompareOperator node, Object data);
  public Object visit(OLtOperator node, Object data);
  public Object visit(OGtOperator node, Object data);
  public Object visit(ONeOperator node, Object data);
  public Object visit(ONeqOperator node, Object data);
  public Object visit(OGeOperator node, Object data);
  public Object visit(OLeOperator node, Object data);
  public Object visit(OLikeOperator node, Object data);
  public Object visit(OLuceneOperator node, Object data);
  public Object visit(OContainsKeyOperator node, Object data);
  public Object visit(OContainsValueOperator node, Object data);
  public Object visit(OEqualsCompareOperator node, Object data);
  public Object visit(OBinaryCondition node, Object data);
  public Object visit(OContainsValueCondition node, Object data);
  public Object visit(OInstanceofCondition node, Object data);
  public Object visit(OBetweenCondition node, Object data);
  public Object visit(OIsNullCondition node, Object data);
  public Object visit(OIsNotNullCondition node, Object data);
  public Object visit(OIsDefinedCondition node, Object data);
  public Object visit(OIsNotDefinedCondition node, Object data);
  public Object visit(OContainsCondition node, Object data);
  public Object visit(OInOperator node, Object data);
  public Object visit(OInCondition node, Object data);
  public Object visit(ONotInCondition node, Object data);
  public Object visit(OContainsAllCondition node, Object data);
  public Object visit(OContainsTextCondition node, Object data);
  public Object visit(OMatchesCondition node, Object data);
  public Object visit(OOrderBy node, Object data);
  public Object visit(OGroupBy node, Object data);
  public Object visit(OLimit node, Object data);
  public Object visit(OSkip node, Object data);
  public Object visit(OTimeout node, Object data);
  public Object visit(OWait node, Object data);
  public Object visit(ORetry node, Object data);
  public Object visit(OCollection node, Object data);
  public Object visit(OFetchPlan node, Object data);
  public Object visit(OFetchPlanItem node, Object data);
  public Object visit(OTraverseProjectionItem node, Object data);
  public Object visit(OJson node, Object data);
}
/* JavaCC - OriginalChecksum=8d3793a570f491a71e55822e64ecdcef (do not edit this line) */
