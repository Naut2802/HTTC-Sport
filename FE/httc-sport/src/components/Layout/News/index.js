import { Box, Breadcrumbs, Card, CardContent, CardMedia, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import logo from "../../Images/logo.png";
import tin_1 from "../../Images/tin-tuc-1.jpg";

function New() {
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
      <div>
        <Card sx={{ display: "flex", maxWidth: "auto" }}>
          <CardMedia
            component="img"
            sx={{ maxWidth: 250, borderRadius: "5px" }}
            image={tin_1}
            alt="Sân Minh Nghiệm"
            className="img-fluid m-2"
          />
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            <CardContent sx={{ flex: "1 0 auto" }}>
              <Typography
                className="text-decoration-none text-dark fs-3 fw-bold"
                variant="h6"
                noWrap
                component={Link}
                to="https://vnexpress.net/anh-dai-dien-cho-bang-dau-te-nhat-lich-su-euro-4762943.html">
                Anh đại diện cho bảng đấu tệ nhất lịch sử Euro
              </Typography>
            </CardContent>
          </Box>
        </Card>
      </div>
    </div>
  );
}

export default New;
