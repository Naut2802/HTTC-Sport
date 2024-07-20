import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import StarRateIcon from "@mui/icons-material/StarRate";
import StorefrontSharpIcon from "@mui/icons-material/StorefrontSharp";
import WifiSharpIcon from "@mui/icons-material/WifiSharp";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";

import { Breadcrumbs, Typography } from "@mui/material";
import { Link } from "react-router-dom";

import sanQN_1 from "../../Images/sanquynhnhu/anh_san_1_1.png";
import logo from "../../Images/logo.png";

function San() {
  return (
    <div className="my-3 container">
      <div className="d-flex justify-content-center">
        <img src={logo} alt="" style={{ width: 120 }} />
      </div>
      <Typography className="fs-3 fw-bold mt-3 mb-2">Danh Sách Sân</Typography>
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
          to="/san-bong">
          Danh Sách Sân
        </Typography>
      </Breadcrumbs>
      <div className="row">
        <div className="col-2 mt-2">
          <Accordion className="mt-2 mb-2">
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1-content"
              id="panel1-header">
              <Typography className="fs-5">Đánh Giá</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <FormGroup>
                <FormControlLabel control={<Checkbox defaultChecked />} label="5 Sao" />
                <FormControlLabel control={<Checkbox />} label="4 Sao" />
                <FormControlLabel control={<Checkbox />} label="3 Sao" />
                <FormControlLabel control={<Checkbox />} label="2 Sao" />
                <FormControlLabel control={<Checkbox />} label="1 Sao" />
              </FormGroup>
            </AccordionDetails>
          </Accordion>
          <Accordion className="mb-2">
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2-content"
              id="panel2-header">
              <Typography className="fs-5">Loại Sân</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <FormGroup>
                <FormControlLabel control={<Checkbox defaultChecked />} label="5 Người" />
                <FormControlLabel control={<Checkbox />} label="7 Người" />
              </FormGroup>
            </AccordionDetails>
          </Accordion>
        </div>
        <div className="col-10 mt-2">
          <Card sx={{ display: "flex", maxWidth: "auto" }}>
            <Typography
              className="text-decoration-none text-dark fs-3 fw-bold"
              variant="h6"
              noWrap
              component={Link}
              to="/chi-tiet-san">
              <CardMedia
                component="img"
                sx={{ maxWidth: 300 }}
                image={sanQN_1}
                alt="Sân Minh Nghiệm"
                className="img-fluid"
              />
            </Typography>

            <Box sx={{ display: "flex", flexDirection: "column" }}>
              <CardContent sx={{ flex: "1 0 auto" }}>
                <Typography
                  className="text-decoration-none text-dark fs-3 fw-bold"
                  variant="h6"
                  noWrap
                  component={Link}
                  to="/chi-tiet-san">
                  Sân Minh Nghiệm
                </Typography>
                <Typography variant="subtitle1" color="text.secondary">
                  <span className="fw-bold fs-5">Loại Sân :</span> Sân 5
                  <br />
                </Typography>
                <Typography variant="subtitle1" color="text.secondary">
                  <span className="fw-bold fs-5">Đánh Giá :</span> 5
                  <StarRateIcon sx={{ width: 15, marginBottom: 1, color: "#FFC107" }} />
                  <br />
                </Typography>
                <Typography variant="subtitle1" color="text.secondary">
                  <span className="fw-bold fs-5">Giá : </span>
                  <span className="fw-bold text-danger fs-5">250.000 </span> /Giờ
                  <br />
                </Typography>
                <Typography variant="subtitle1" color="text.secondary">
                  <span className="fw-bold fs-5">Địa Chỉ :</span> 202 Hoàng Văn Thụ, P. 9, Quận Phú
                  Nhuận - Hồ Chí Minh
                </Typography>
                <Typography variant="subtitle1" color="text.secondary">
                  <span className="fs-6">
                    Căn Tin
                    <StorefrontSharpIcon sx={{ marginLeft: 1, marginRight: 1 }} />
                  </span>
                  <span className="fs-6 border-start">
                    <WifiSharpIcon sx={{ marginRight: 1, marginLeft: 1 }} />
                    Wifi
                  </span>
                </Typography>
              </CardContent>
            </Box>
          </Card>
        </div>
      </div>
    </div>
  );
}

export default San;
