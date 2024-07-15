import ThumbS from "./ThumbS.jsx";
import { sanImages } from "../dataImageThumbs.js";

import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/thumbs";

function ThumbSlider() {
  return (
    <div>
      <div
        style={{
          width: "auto",
          backgroundColor: "#fff",
          padding: "20px",
          borderRadius: "5px",
        }}>
        <ThumbS images={sanImages} />
      </div>
    </div>
  );
}

export default ThumbSlider;
