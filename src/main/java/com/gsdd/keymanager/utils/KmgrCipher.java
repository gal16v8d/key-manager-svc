package com.gsdd.keymanager.utils;

import com.gsdd.cipher.CipherAlgorithm;
import com.gsdd.cipher.CipherUtil;
import com.gsdd.cipher.DigestAlgorithm;
import java.util.function.UnaryOperator;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class KmgrCipher {

  public static final String KM_SALT = "8ad7d96acc9ce8d129df900ec373c1399d29fb99";

  public static final UnaryOperator<String> ENCODE = data -> CipherUtil
      .encode(data, KM_SALT, DigestAlgorithm.SHA512, CipherAlgorithm.AES_WITH_PADDING);

  public static final UnaryOperator<String> DECODE = data -> CipherUtil
      .decode(data, KM_SALT, DigestAlgorithm.SHA512, CipherAlgorithm.AES_WITH_PADDING);

}
