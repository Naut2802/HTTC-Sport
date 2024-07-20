import { Box, Breadcrumbs, Button, Card, CardContent, Typography } from "@mui/material";
import { Link } from "react-router-dom";

import PhoneIphoneIcon from "@mui/icons-material/PhoneIphone";
import FacebookIcon from "@mui/icons-material/Facebook";
import SendIcon from "@mui/icons-material/Send";
import RoomIcon from "@mui/icons-material/Room";
import logo from "../../Images/logo.png";

function Contact() {
  return (
    <div className="my-3 container">
      <div className="d-flex justify-content-center">
        <img src={logo} alt="" style={{ width: 120 }} />
      </div>
      <Typography className="fs-3 fw-bold mt-3 mb-2">Tin Tức</Typography>
      <Breadcrumbs aria-label="breadcrumb" className="fs-5 mb-2">
        <Typography
          className="text-decoration-none text-dark"
          variant="h6"
          noWrap
          component={Link}
          to="/trang-chu">
          Trang Chủ
        </Typography>
        <Typography
          className="text-decoration-none text-dark"
          variant="h6"
          noWrap
          component={Link}
          to="/tin-tuc">
          Tin Tức
        </Typography>
      </Breadcrumbs>
      <div className="row">
        <Card sx={{ display: "flex", maxWidth: "auto", width: "100%" }}>
          <div className="col-6 border rounded-2 my-2">
            <Box sx={{ display: "flex", flexDirection: "column", height: "100%" }}>
              <CardContent>
                <Typography
                  className="text-decoration-none text-center text-dark fw-bold"
                  variant="h6"
                  noWrap>
                  Thông Tin Liên Hệ
                </Typography>
                <Typography
                  className="text-decoration-none text-dark"
                  variant="body1"
                  component="div">
                  <Button
                    className="text-decoration-none text-dark"
                    variant="text"
                    startIcon={<PhoneIphoneIcon />}>
                    0376 549 059
                  </Button>
                </Typography>
                <Typography
                  className="text-decoration-none text-dark"
                  variant="body1"
                  component="div">
                  <Button
                    className="text-decoration-none text-dark"
                    variant="text"
                    startIcon={<SendIcon />}>
                    <span className="text-lowercase">leton1629@gmail.com</span>
                  </Button>
                </Typography>
                <Typography
                  className="text-decoration-none text-dark"
                  variant="body1"
                  component="div"
                  sx={{ display: "flex", alignItems: "center" }}>
                  <Button
                    className="text-decoration-none text-dark"
                    variant="text"
                    startIcon={<FacebookIcon />}
                    component={Link}
                    to="https://www.facebook.com/ton.le.1656">
                    <span className="text-capitalize">Tôn Lê</span>
                  </Button>
                </Typography>
                <Typography
                  className="text-decoration-none text-dark"
                  variant="body1"
                  component="div">
                  <Button
                    className="text-decoration-none text-dark"
                    variant="text"
                    startIcon={<RoomIcon />}
                    component={Link}
                    to="https://www.facebook.com/ton.le.1656">
                    <span className="text-capitalize">
                      Công viên phần mềm Quang Trung, P. Tân Chánh Hiệp, Q.12, TP.HCM, Việt Nam
                    </span>
                  </Button>
                </Typography>
              </CardContent>
            </Box>
          </div>
          <div className="col-6 d-flex justify-content-end my-2">
            <Typography>
              <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.4443841931507!2d106.62606566784763!3d10.853765974127208!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752b6c59ba4c97%3A0x535e784068f1558b!2zVHLGsOG7nW5nIENhbyDEkeG6s25nIEZQVCBQb2x5dGVjaG5pYw!5e0!3m2!1svi!2s!4v1719417822130!5m2!1svi!2s"
                width="600"
                height="450"
                title="map"
                style={{ border: 0, borderRadius: "5px" }}
                allowFullScreen
                loading="lazy"
                referrerPolicy="no-referrer-when-downgrade"
              />
            </Typography>
          </div>
        </Card>
      </div>
    </div>
  );
}

export default Contact;
