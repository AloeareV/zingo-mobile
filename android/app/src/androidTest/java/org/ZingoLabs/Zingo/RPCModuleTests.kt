package org.ZingoLabs.Zingo

import android.util.Base64
import java.io.InputStream
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

//test dependencies
import com.google.common.truth.Truth.assertThat
import org.junit.Test

data class Addresses (
	val address : String,
	val receivers : Receivers
)

data class Receivers (
	val transparent : String,
	val sapling : String,
	val orchard_exists : Boolean
)

@Test
fun executeAddresses() {  
    val server = "https://mainnet.lightwalletd.com:9067"
    val seed = "abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon art"
    val birthday = "1"
    val datadir = MainApplication.getAppContext()!!.getFilesDir().getPath()

    val saplingSpendFile: InputStream = MainApplication.getAppContext()?.resources?.openRawResource(R.raw.saplingspend)!!
    var saplingSpend = saplingSpendFile.readBytes()
    saplingSpendFile.close()

    val middle0 =        0
    val middle1 =  6000000 // 6_000_000 - 8 pieces
    val middle2 = 12000000
    val middle3 = 18000000
    val middle4 = 24000000
    val middle5 = 30000000
    val middle6 = 36000000
    val middle7 = 42000000
    val middle8: Int = saplingSpend.size
    var saplingSpendEncoded = StringBuilder(Base64.encodeToString(saplingSpend, middle0, middle1 - middle0, Base64.NO_WRAP))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle1,
        middle2 - middle1,
        Base64.NO_WRAP
    ))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle2,
        middle3 - middle2,
        Base64.NO_WRAP
    ))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle3,
        middle4 - middle3,
        Base64.NO_WRAP
    ))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle4,
        middle5 - middle4,
        Base64.NO_WRAP
    ))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle5,
        middle6 - middle5,
        Base64.NO_WRAP
    ))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle6,
        middle7 - middle6,
        Base64.NO_WRAP
    ))
    saplingSpendEncoded = saplingSpendEncoded.append(Base64.encodeToString(
        saplingSpend,
        middle7,
        middle8 - middle7,
        Base64.NO_WRAP
    ))

    saplingSpend = ByteArray(0)

    val saplingOutputFile: InputStream = MainApplication.getAppContext()?.resources?.openRawResource(R.raw.saplingoutput)!!
    var saplingOutput = saplingOutputFile.readBytes()
    saplingOutputFile.close()

    val saplingOutputEncoded = StringBuilder(Base64.encodeToString(saplingOutput, Base64.NO_WRAP))

    saplingOutput = ByteArray(0)

    RustFFI.initfromseed(server, seed, birthday,
        saplingOutputEncoded.toString(),
        saplingSpendEncoded.toString(),
        datadir)

    val resp = RustFFI.execute("addresses", "")
    val addresses: List<Addresses> = jacksonObjectMapper().readValue(resp)

    assertThat(addresses[0].address).isEqualTo("u16sw4v6wy7f4jzdny55yzl020tp3yqg3c85dc6n7mmq0urfm6adqg79hxmyk85ufn4lun4pfh5q48cc3kvxhxm3w978eqqecdd260gkzjrkun6z7m9mcrt2zszaj0mvk6ufux2zteqwh57cq906hz3rkg63duaeqsvjelv9h5srct0zq8rvlv23wz5hed7zuatqd7p6p4ztugc4t4w2g")
    assertThat(addresses[0].receivers.transparent).isEqualTo("t1dUDJ62ANtmebE8drFg7g2MWYwXHQ6Xu3F")
    assertThat(addresses[0].receivers.sapling).isEqualTo("zs16uhd4mux24se6wkm74vld0ec63d4dxt3d7m80l5xytreplkkllrrf9c7fj859mhp8tkcq9hxfvj")
    assertThat(addresses[0].receivers.orchard_exists).isEqualTo(true)
}
