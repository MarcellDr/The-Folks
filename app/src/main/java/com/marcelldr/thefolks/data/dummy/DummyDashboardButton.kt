package com.marcelldr.thefolks.data.dummy

import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.domain.model.DashboardButton

object DummyDashboardButton {
    val listDashboardButton = listOf(
        DashboardButton(
            imageDrawable = R.drawable.icon_police,
            title = "SIM",
            logoUrl = "http://sim.korlantas.polri.go.id/devregistrasi/assets/img/logo.png",
            description = "Di Indonesia, Surat Izin Mengemudi (SIM) adalah bukti registrasi dan identifikasi yang diberikan oleh Polri kepada seseorang yang telah memenuhi persyaratan administrasi, sehat jasmani dan rohani, memahami peraturan lalu lintas dan terampil mengemudikan kendaraan bermotor. Setiap orang yang mengemudikan Kendaraan Bermotor di Jalan wajib memiliki Surat Izin Mengemudi sesuai dengan jenis Kendaraan Bermotor yang dikemudikan (Pasal 77 ayat (1) UU No.22 Tahun 2009). ",
            linkUrl = "http://sim.korlantas.polri.go.id/devregistrasi/index.php/registrasi"
        ),
        DashboardButton(
            imageDrawable = R.drawable.icon_doctor,
            title = "BPJS"
        ),
        DashboardButton(
            imageDrawable = R.drawable.icon_service,
            title = "NPWP"
        ),
        DashboardButton(
            imageDrawable = R.drawable.icon_student,
            title = "Prakerja"
        )
    )
}