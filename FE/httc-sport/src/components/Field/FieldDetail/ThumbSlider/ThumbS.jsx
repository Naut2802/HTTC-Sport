import PropTypes from "prop-types";
import "./ThumbSlider.scss";

import { Swiper, SwiperSlide } from "swiper/react";
import { useState } from "react";
import { sanImages } from "../dataImageThumbs";
import { Thumbs, Navigation } from "swiper/modules";

const ThumbSliders = (props) => {
  const [activeThumb, setActiveThumb] = useState();
  return (
    <div>
      <Swiper
        loop={true}
        spaceBetween={10}
        navigation={true}
        modules={[Navigation, Thumbs]}
        grabCursor={true}
        thumbs={{ swiper: activeThumb }}
        className="san-images-slider"
        style={{ height: 450 }}>
        {props.images.map((item, index) => (
          <SwiperSlide key={item}>
            <img src={item} alt="product images" />
          </SwiperSlide>
        ))}
      </Swiper>
      <Swiper
        onSwiper={setActiveThumb}
        loop={true}
        spaceBetween={10}
        slidesPerView={4}
        modules={[Navigation, Thumbs]}
        className="san-images-slider-thumbs"
        style={{ height: 90 }}>
        {props.images.map((item, index) => (
          <SwiperSlide key={index}>
            <div className="san-images-slider-thumbs-wrapper">
              <img src={item} alt="product images" />
            </div>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

sanImages.propTypes = {
  images: PropTypes.array.isRequired,
};

export default ThumbSliders;

