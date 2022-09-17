package com.gsdd.keymanager.utils;

import com.gsdd.cypher.CypherAlgorithm;
import com.gsdd.cypher.CypherUtil;
import com.gsdd.cypher.DigestAlgorithm;
import java.util.function.UnaryOperator;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class KmgrCypher {

  public static final String KM_SALT = "8ad7d96acc9ce8d129df900ec373c1399d29fb99";

  public static final UnaryOperator<String> CYPHER = data -> CypherUtil.encode(data, KM_SALT,
      DigestAlgorithm.SHA512, CypherAlgorithm.AES_WITH_PADDING);

  public static final UnaryOperator<String> DECYPHER = data -> CypherUtil.decode(data, KM_SALT,
      DigestAlgorithm.SHA512, CypherAlgorithm.AES_WITH_PADDING);

}
