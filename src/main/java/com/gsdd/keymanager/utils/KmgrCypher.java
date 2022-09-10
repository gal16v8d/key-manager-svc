package com.gsdd.keymanager.utils;

import com.gsdd.cypher.CypherAlgorithm;
import com.gsdd.cypher.CypherUtil;
import com.gsdd.cypher.DigestAlgorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class KmgrCypher {

  public static final String KM_SALT = "8ad7d96acc9ce8d129df900ec373c1399d29fb99";

  public static String cypher(String dato) {
    return CypherUtil.encode(
        dato, KM_SALT, DigestAlgorithm.SHA512, CypherAlgorithm.AES_WITH_PADDING);
  }

  public static String decypher(String dato) {
    return CypherUtil.decode(
        dato, KM_SALT, DigestAlgorithm.SHA512, CypherAlgorithm.AES_WITH_PADDING);
  }
}
